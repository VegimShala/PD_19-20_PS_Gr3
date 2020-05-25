package unipr.fshmn.simora.controllers;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import unipr.fshmn.simora.db.*;

import javax.xml.bind.Element;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path="/add") // Map ONLY POST Requests
    public ModelAndView addNewUser (@RequestParam String department, @RequestParam String room, @RequestParam String subject
            , @RequestParam Long ID, @RequestParam String startDate,@RequestParam String endDate) {

        Schedule s = new Schedule();
        s.setUserID(ID);
        s.setDepartment(department);
        s.setRoomID(getRoomId(room));
        s.setSubject(subject);
        s.setStartDate(LocalDateTime.parse(startDate));
        s.setEndDate(LocalDateTime.parse(endDate));
        ModelAndView modelAndView=new ModelAndView();
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        boolean hasElements = false;
        boolean isValid = false;
        if(inDB(ID)) {
            Iterable<Schedule> schedules = scheduleRepository.findByRoomID(getRoomId(room));

            for (Schedule element : schedules) {
                hasElements = true;
            }

            for(Schedule schedule: schedules)
            {
                if(start.isBefore(end)) {
                    LocalDateTime startN = LocalDateTime.parse(schedule.getStartDate().substring(0,10) + "T"+
                            schedule.getStartDate().substring(11,schedule.getStartDate().length()));
                    LocalDateTime endN = LocalDateTime.parse(schedule.getEndDate().substring(0,10) + "T"+
                            schedule.getEndDate().substring(11,schedule.getEndDate().length()));
                    if (!((start.isBefore(startN) && (end.isBefore(startN)))
                            || (start.isAfter(endN))))
                    {
                        isValid = true;
                    }
                    else
                    {
                        modelAndView.setViewName("Overlapping");
                    }
                }
                else
                {
                    modelAndView.setViewName("DateError");
                }
            }
        }
        else
        {
            modelAndView.setViewName("professorNotFound");
        }
        if(isValid || !hasElements){
        scheduleRepository.save(s);
        modelAndView.setViewName("scheduleAdded");}
        return modelAndView;
    }

    public boolean inDB(Long ID)
    {
        boolean answer = false;
        Iterable<User> users = userRepository.findAll();
        for(User user : users)
        {
            if(ID.equals(user.getID()))
            {
                answer = true;
            }
        }
        return answer;
    }

    @RequestMapping("/remove")
    public ModelAndView removeSchedules(@RequestParam Long scheduleID)
    {
        Schedule schedule;
        if (scheduleRepository.findById(scheduleID).isPresent())
        {
            schedule = scheduleRepository.findById(scheduleID).get();
            scheduleRepository.delete(schedule);
        }
        else
        {
            System.out.println("This Schedule ID doesn't exist");
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("scheduleRemoved");
        return modelAndView;
    }

    @RequestMapping("/filter")
    public ModelAndView showSchedules(@RequestParam String date, @RequestParam String department, @RequestParam String room, @RequestParam String professor)
    {
        if(date.equals("1970-10-01")){ date = ""; }
        if(department.equals("Zgjedh Departamentin")) { department = ""; }
        if(room.equals("Zgjedh Sallen")) { room = ""; }
        professor = professor.trim();
        Iterable<ScheduleDTO> schedules = (Iterable<ScheduleDTO>)
                scheduleRepository.gjej
                        ("%"+date+"%","%"+department+"%","%"+room+"%","%"+professor+"%").stream().map(this::toDTO).collect(Collectors.toList());

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("schedules",schedules);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Long getRoomId(String s)
    {
        Long roomID = 0l;
        Iterable<Room> rooms = roomRepository.findAll();
        for(Room room : rooms)
        {
            if(s.equals(room.getRoom()))
            {
                roomID = room.getID();
            }
        }
        return roomID;
    }

    public String getRoomName(Long ID)
    {
        String roomName = "";
        Iterable<Room> rooms = roomRepository.findAll();
        for(Room room : rooms)
        {
            if(ID == room.getID())
            {
                roomName = room.getRoom();
            }
        }
        return roomName;
    }

    public ScheduleDTO toDTO(Schedule schedule)
    {
        ScheduleDTO obj = new ScheduleDTO();
        obj.setDepartment(schedule.getDepartment());
        obj.setEndDate(schedule.getEndDate());
        obj.setStartDate(schedule.getStartDate());
        obj.setID(schedule.getID());
        obj.setSubject(schedule.getSubject());
        obj.setUserID(schedule.getUserID());
        obj.setRoomName(getRoomName(schedule.getRoomID()));
        return obj;
    }





}