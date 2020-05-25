package unipr.fshmn.simora.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import unipr.fshmn.simora.db.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        scheduleRepository.save(s);
        ModelAndView modelAndView=new ModelAndView();

        modelAndView.setViewName("scheduleAdded");
        //modelAndView.setViewName("index");
        return modelAndView;
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

    @RequestMapping("/qyshdush")
    public ModelAndView showSchedules(@RequestParam String date, @RequestParam String department, @RequestParam String room, @RequestParam String professor)
    {
        System.out.println(date);
        System.out.println(department);
        System.out.println(room);
        System.out.println(professor);

        if(date.equals("1970-10-01")){ date = ""; }
        if(department.equals("Zgjedh Departamentin")) { department = ""; }
        if(room.equals("Zgjedh Sallen")) { room = ""; }
        professor = professor.trim();
        System.out.println(date);
        System.out.println(department);
        Iterable<ScheduleDTO> schedules = (Iterable<ScheduleDTO>)
                scheduleRepository.gjej
                        ("%"+date+"%","%"+department+"%","%"+room+"%","%"+professor+"%").stream().map(this::toDTO).collect(Collectors.toList());

        if(!room.equals("Zgjedh Sallen"))
        {
            Long roomID = getRoomId(room);
            if(!professor.trim().equals(""))
            {
                Long profId = 0l;
                try {profId = Long.parseLong(professor);}
                catch (NumberFormatException e) {System.out.println("Couldn't parse to Long");}

                schedules = (Iterable<ScheduleDTO>) scheduleRepository.findByStartDateContainingAndDepartmentContainingAndRoomIDLikeAndUserIDLike
                                (date,department,roomID,profId).stream().map(this::toDTO).collect(Collectors.toList());
            }
            else
            {
                schedules = (Iterable<ScheduleDTO>) scheduleRepository.findByStartDateContainingAndDepartmentAndRoomID
                                (date,department,roomID).stream().map(this::toDTO).collect(Collectors.toList());
            }
        }
        else
        {
            if(!professor.trim().equals("")) {
                Long profId = 0l;
                try {
                    profId = Long.parseLong(professor);
                } catch (NumberFormatException e) {
                    System.out.println("Couldn't parse to Long");
                }

                schedules = (Iterable<ScheduleDTO>) scheduleRepository.findByStartDateContainingAndDepartmentAndUserID
                                (date, department, profId).stream().map(this::toDTO).collect(Collectors.toList());
            }
        }

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