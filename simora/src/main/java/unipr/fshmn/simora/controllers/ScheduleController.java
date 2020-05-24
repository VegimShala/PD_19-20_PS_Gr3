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
        modelAndView.setViewName("indexA");
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

}
