package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.company.dao.CandidateDAO;
import com.company.dao.ContactDAO;
import com.company.dao.SkillDAO;
import com.company.entities.Candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/com_company")
public class MainController {

    @Autowired
    private CandidateDAO candidateDAO;

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private SkillDAO skillDAO;

    private static final String SKILL_PARAM_PATTERN = "skill_";
    private static final String RATE_PARAM_PATTERN = "rate";
    private static final int MAX_SKILLS = 10;
    private static final int MAX_CANDIDATES_ON_PAGE = 20;

    private ModelAndView listInfo(){
        List<Candidate> list = candidateDAO.getList();
        int pages = list.size()% MAX_CANDIDATES_ON_PAGE == 0 ? list.size()/ MAX_CANDIDATES_ON_PAGE : list.size()/ MAX_CANDIDATES_ON_PAGE +1;

        Map<String, Object> model = new HashMap<>();
        model.put("candidates", list.subList(0, Math.min(list.size(), MAX_CANDIDATES_ON_PAGE)));
        model.put("pages", pages);
        model.put("page", 1);
        return new ModelAndView("index", model);
    }

    /**
     * Method sorts List<Candidate> by Date using candidateDAO.sortByDate(), and returns index page with paging number 1.
     * It ia default sorting.
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView candidatesList() {
        candidateDAO.sortByDate();

        return listInfo();
    }


    /**
     * Method returns ModelAndView with List<Candidate> from cache, number of page, ant pages amount(list.size()/ MAX_CANDIDATES_ON_PAGE).
     *
     * @param page int
     * @return ModelAndView
     */
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    public ModelAndView paging(@RequestParam(value="page") int page) {
        List<Candidate> list = candidateDAO.getList();

        int pages = list.size()% MAX_CANDIDATES_ON_PAGE == 0 ? list.size()/ MAX_CANDIDATES_ON_PAGE : list.size()/ MAX_CANDIDATES_ON_PAGE +1;

        Map<String, Object> model = new HashMap<>();
        model.put("candidates", list.subList((page-1)* MAX_CANDIDATES_ON_PAGE, Math.min(list.size(), page* MAX_CANDIDATES_ON_PAGE)));
        model.put("pages", pages);
        model.put("page", page);
        return new ModelAndView("index", model);
    }


    /**
     * Method sorts List<Candidate> by Name using candidateDAO.sortByName(), and returns index page with paging number 1.
     *
     * @return ModelAndView
     */
    @RequestMapping("/sortByName")
    public ModelAndView candidatesListByName() {
        candidateDAO.sortByName();

        return listInfo();
    }


    /**
     * Method redirects to page where user can add new candidate.
     *
     * @return ModelAndView
     */
    @RequestMapping("/add")
    public ModelAndView addTest() {
        return new ModelAndView ("add");
    }


    /**
     * Method searched all Candidates that include incoming String pattern using method candidateDAO.sortByPattern(String).
     * And redirect to the main page(index).
     *
     * @param pattern String
     * @return ModelAndView
     */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(value="pattern") String pattern) {
        candidateDAO.sortByPattern(pattern);

        return listInfo();
	}


    /**
     * Deleting candidate by id from database. Also will be deleted all entities that aggregated and mapped by
     * Candidate(Skill and Contact), because cascade = CascadeType.ALL.
     *
     * @param id int
     * @return ModelAndView
     */
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam(value="id") int id) {
        candidateDAO.delete(id);

        return listInfo();
	}


    /**
     * Method redirects to page where user can find all info about candidate by his id.
     *
     * @param id int
     * @return ModelAndView
     */
    @RequestMapping("/candidateInfo")
    public ModelAndView info(@RequestParam(value="id") int id) {
        return new ModelAndView("candidateInfo", "candidate", candidateDAO.getById(id));
    }


    /**
     * This method all information about new candidate from huge form.
     * Method will find and add all declared info about candidate, his contacts and skills. You must know, that skills and
     * contacts amount depends. Some fields must be filled? but not all.
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/addCandidate", method = RequestMethod.POST)
    public ModelAndView addInfoTest(@RequestParam Map<String,String> params){
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
            String skill = SKILL_PARAM_PATTERN + i;
            String rate = RATE_PARAM_PATTERN + i;
            if(params.containsKey(skill) && params.containsKey(rate)) {
                skillDAO.add(c, params.get(skill), params.get(rate));
            }
        }

        return listInfo();
    }
}