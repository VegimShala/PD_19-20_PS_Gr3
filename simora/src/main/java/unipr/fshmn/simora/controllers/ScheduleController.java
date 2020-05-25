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
        //modelAndView.setViewName("indexA");
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

    public Schedule getSchedule(Long ID)
    {
        Schedule schedule = new Schedule();
        Iterable<Schedule> schedules =  scheduleRepository.findAll();
        for(Schedule s : schedules)
        {
            if(ID.equals(s.getID()))
            {
                schedule = s;
            }
        }
        System.out.println(schedule.getID());
        return schedule;
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

    /*@RequestMapping(path="/indexP") // Map ONLY POST Requests
    public ModelAndView addNewUser (@RequestParam String date, @RequestParam String department, @RequestParam String room) {


        s.setDepartment(department);
        s.setRoomID(getRoomId(room));

        scheduleRepository.save(s);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("indexA");
        return modelAndView;
    }*/

    @RequestMapping("/indexP")
    public ModelAndView schedules()
    {
        //Ne baze te USER-it, pjesa tjeter eshte krejt e njejte
        Iterable<ScheduleDTO> schedules = (Iterable<ScheduleDTO>)
                scheduleRepository.findBySubjectAndDepartment("Programimi i Distribuuar","Matematike").stream().map(this::toDTO).collect(Collectors.toList());
        /*for(Schedule schedule : schedules)
        {
            schedule.setRoomID(getRoomName(schedule.getRoomID()));
        }*/
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("schedules",schedules);
        modelAndView.setViewName("indexP");
        return modelAndView;
    }

    @RequestMapping()
    public ModelAndView removeSchedules(@RequestParam Long scheduleID)
    {
        System.out.println(6565);
        Schedule schedule = getSchedule(scheduleID);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("schedule",schedule);
        modelAndView.setViewName("redirect:indexA");
        return modelAndView;
    }


}