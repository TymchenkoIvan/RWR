package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.company.dao.CandidateDAO;
import com.company.dao.ContactDAO;
import com.company.dao.SkillDAO;
import com.company.entities.Candidate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/com_company")
public class MainController {

    private static final String SKILL_PARAM = "skill_";
    private static final String RATE_PARAM = "rate";
    private static final int MAX_SKILLS = 10;
    private static final int MAX_LIST = 7;

    @Autowired
    private CandidateDAO candidateDAO;

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private SkillDAO skillDAO;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView candidatesList() {
        candidateDAO.sortByDate();

        List<Candidate> list = candidateDAO.getList();
        int pages = list.size()%MAX_LIST == 0 ? list.size()/MAX_LIST : list.size()/MAX_LIST +1;

        Map<String, Object> model = new HashMap<>();
        model.put("candidates", list.subList(0, Math.min(list.size(), MAX_LIST)));
        model.put("pages", pages);
        model.put("page", 1);
        return new ModelAndView("index", model);
    }

    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    public ModelAndView paging(@RequestParam(value="page") int page) {
        List<Candidate> list = candidateDAO.getList();

        int pages = list.size()%MAX_LIST == 0 ? list.size()/MAX_LIST : list.size()/MAX_LIST +1;

        Map<String, Object> model = new HashMap<>();
        model.put("candidates", list.subList((page-1)*MAX_LIST, Math.min(list.size(), page*MAX_LIST)));
        model.put("pages", pages);
        model.put("page", page);
        return new ModelAndView("index", model);
    }

    @RequestMapping("/index")
    public String indexTest() {
        return "redirect: /";
    }

    @RequestMapping("/sortByName")
    public ModelAndView candidatesListByName() {
        candidateDAO.sortByName();

        List<Candidate> list = candidateDAO.getList();
        int pages = list.size()%MAX_LIST == 0 ? list.size()/MAX_LIST : list.size()/MAX_LIST +1;

        Map<String, Object> model = new HashMap<>();
        model.put("candidates", list.subList(0, Math.min(list.size(), MAX_LIST)));
        model.put("pages", pages);
        model.put("page", 1);
        return new ModelAndView("index", model);
    }

    @RequestMapping("/add")
    public ModelAndView addTest() {
        return new ModelAndView ("add");
    }

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(value="pattern") String pattern) {
        candidateDAO.sortByPattern(pattern);

        List<Candidate> list = candidateDAO.getList();
        int pages = list.size()%MAX_LIST == 0 ? list.size()/MAX_LIST : list.size()/MAX_LIST +1;

        Map<String, Object> model = new HashMap<>();
        model.put("candidates", list.subList(0, Math.min(list.size(), MAX_LIST)));
        model.put("pages", pages);
        model.put("page", 1);
        return new ModelAndView("index", model);
	}

	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam(value="id") int id) {
        candidateDAO.delete(id);

        List<Candidate> list = candidateDAO.getList();
        int pages = list.size()%MAX_LIST == 0 ? list.size()/MAX_LIST : list.size()/MAX_LIST +1;

        Map<String, Object> model = new HashMap<>();
        model.put("candidates", list.subList(0, Math.min(list.size(), MAX_LIST)));
        model.put("pages", pages);
        model.put("page", 1);
        return new ModelAndView("index", model);
	}

    @RequestMapping("/candidateInfo")
    public ModelAndView info(@RequestParam(value="id") int id) {
        return new ModelAndView("candidateInfo", "candidate", candidateDAO.getById(id));
    }

    @RequestMapping(value = "/addCandidate", method = RequestMethod.POST)
    public ModelAndView addInfoTest(@RequestParam Map<String,String> params)
    {
        // adding user
        Candidate c = candidateDAO.addAndGet(params.get("firstName"), params.get("lastName"), params.get("interviewDate"));

        // adding contacts
        if(params.containsKey("telephone")){
            contactDAO.add(c, "telephone", params.get("telephone"));
        }
        if(params.containsKey("mail")) {
            contactDAO.add(c, "mail", params.get("mail"));
        }
        if(params.containsKey("skype")) {
            contactDAO.add(c, "skype", params.get("skype"));
        }
        if(params.containsKey("other1") && params.containsKey("contact1")) {
            contactDAO.add(c, params.get("other1"), params.get("contact1"));
        }
        if(params.containsKey("other2") && params.containsKey("contact2")) {
            contactDAO.add(c, params.get("other2"), params.get("contact2"));
        }

        // adding skills
        for(int i=1; i<=MAX_SKILLS; i++){
            String skill = SKILL_PARAM + i;
            String rate = RATE_PARAM + i;
            if(params.containsKey(skill) && params.containsKey(rate)) {
                skillDAO.add(c, params.get(skill), params.get(rate));
            }
        }

        List<Candidate> list = candidateDAO.getList();

        int pages = list.size()%MAX_LIST == 0 ? list.size()/MAX_LIST : list.size()/MAX_LIST +1;

        Map<String, Object> model = new HashMap<>();
        model.put("candidates", list.subList(0, Math.min(list.size(), MAX_LIST)));
        model.put("pages", pages);
        model.put("page", 1);
        return new ModelAndView("index", model);
    }
}