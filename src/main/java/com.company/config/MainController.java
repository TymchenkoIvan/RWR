package com.company.config;

import com.company.dao.CandidateDAO;
import com.company.dao.ContactDAO;
import com.company.dao.SkillDAO;
import com.company.entities.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static com.company.enums.Page.*;
import static com.company.enums.Sort.BY_DATE;
import static com.company.enums.Sort.BY_NAME;

@Controller
@RequestMapping("/com_company")
public class MainController {

    @Autowired
    private CandidateDAO candidateDAO;

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private SkillDAO skillDAO;

    public static final String SKILL_PARAM_PATTERN = "skill_";
    public static final String SKILL_RATE_PATTERN = "rate";
    public static final String CONTACT_MAIL_PATTERN = "mail";
    public static final String CONTACT_TYPE_PATTERN = "type";
    public static final String CONTACT_VALUE_PATTERN = "value";
    public static final String ERROR = "error";
    public static final int MAX_SKILLS = 10;
    public static final int MAX_CONTACTS = 4;
    public static final int MAX_CANDIDATES_ON_PAGE = 5;


    /**
     * Method sorts List<Candidate> by Date using candidateDAO.sortByDate(), and returns index page with paging number 1.
     * It ia default sorting.
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView candidatesList() {
        return getModel(BY_DATE.name(), 1);
    }


    /**
     * Method returns ModelAndView with List<Candidate>, number of page, and pages amount.
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    public ModelAndView paging(@RequestParam(value="page") int page,
                               @RequestParam(value="sort") String sort) {
        return getModel(sort, page);
    }


    /**
     * Method returns index page sorted by name with paging number 1.
     *
     * @return ModelAndView
     */
    @RequestMapping("/sortByName")
    public ModelAndView candidatesListByName() {

        return getModel(BY_NAME.name(), 1);
    }


    /**
     * Method redirects to page where user can add new candidate.
     *
     * @return ModelAndView
     */
    @RequestMapping("/add")
    public ModelAndView add() {
        return new ModelAndView (ADD.getAddress());
    }


    /**
     * Method returns index page sorted by pattern with paging number 1.
     *
     * @param pattern String
     * @return ModelAndView
     */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(value="pattern") String pattern) {
        return getModel(pattern, 1);
	}


    /**
     * Deleting candidate by id from database. Also will be deleted all entities that aggregated and mapped by
     * Candidate(Skill and Contact), because cascade = CascadeType.ALL.
     * Method returns index page sorted by date with paging number 1.
     * If method will catch Exception it will return page with error message.
     *
     * @param id int
     * @return ModelAndView
     */
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam(value="id") int id) {
        try {
            candidateDAO.delete(id);
        } catch (Exception e) {
            return getModelError(INFO.getAddress(), e.toString());
        }

        return getModel(BY_DATE.name(), 1);
	}


    /**
     * Method redirects to page where user can find all info about candidate by his id.
     * If method will catch Exception it will return page with error message.
     *
     * @param id int
     * @return ModelAndView
     */
    @RequestMapping("/candidateInfo")
    public ModelAndView info(@RequestParam(value="id") int id) {
        Map<String, Object> model = new HashMap<>();
        try {
            model.put("candidate", candidateDAO.getById(id));
        } catch (Exception e){
            return getModelError(INFO.getAddress(), e.toString());
        }
        return new ModelAndView(INFO.getAddress(), model);
    }


    /**
     * This method add all information about new candidate from huge form.
     * If method can not create new candidate or add his mail will return this page(..add.jsp) with error message.
     *
     * All contacts(excepts MAIL) and skills can be empty.
     *
     * @param params Map <String,String>
     * @return ModelAndView
     */
    @RequestMapping(value = "/addCandidate", method = RequestMethod.POST)
    public ModelAndView addCandidate(@RequestParam Map<String,String> params){
        // adding user and mandatory contacts
        Candidate c;
        try {
            c = candidateDAO.addAndGet(params.get("firstName"), params.get("lastName"), params.get("interviewDate"));
            if(contactDAO.isMailReal(params.get(CONTACT_MAIL_PATTERN))) {
                contactDAO.add(c, CONTACT_MAIL_PATTERN, params.get(CONTACT_MAIL_PATTERN));
            } else {
                candidateDAO.delete(c.getId());
                throw new IllegalStateException("Wrong Mail");
            }
        } catch (Exception e) {
            return getModelError(ADD.getAddress(), e.toString());
        }
        // adding not mandatory contacts
        for(int i=1; i<=MAX_CONTACTS; i++){
            String type = CONTACT_TYPE_PATTERN + i;
            String value = CONTACT_VALUE_PATTERN+ i;
            if(params.containsKey(type) && params.containsKey(value)) {
                contactDAO.add(c, params.get(type), params.get(value));
            }
        }
        // adding skills
        for(int i=1; i<=MAX_SKILLS; i++){
            String skill = SKILL_PARAM_PATTERN + i;
            String rate = SKILL_RATE_PATTERN + i;
            if(params.containsKey(skill) && params.containsKey(rate)) {
                skillDAO.add(c, params.get(skill), params.get(rate));
            }
        }

        return getModel(BY_DATE.name(), 1);
    }

    /**
     * Returns maximum pages amount for all candidates
     *
     * @param count int
     * @return int
     */
    public int getPagesCount(int count){
        return count % MAX_CANDIDATES_ON_PAGE == 0
                ? count/MAX_CANDIDATES_ON_PAGE
                : count/MAX_CANDIDATES_ON_PAGE +1;
    }


    /**
     * Method with main model logic of index page.
     * Method try to find type of sort, and page number to return.
     * ModelAndView contains sorted candidates List, pages amount, number of page and sort type for var in index page.
     * If exception happens method will return index page with exception message using getModelError().
     *
     * @param sort Spring shows type of sorting
     * @param page int
     * @return ModelAndView
     */
    public ModelAndView getModel(String sort, int page){
        try {
            Map<String, Object> model = new HashMap<>();
            if (BY_NAME.name().equals(sort)) {
                model.put("candidates", candidateDAO.sortedByName(page));
                model.put("pages", getPagesCount(candidateDAO.getCandidatesCount()));
            } else if (BY_DATE.name().equals(sort)) {
                model.put("candidates", candidateDAO.sortedByDate(page));
                model.put("pages", getPagesCount(candidateDAO.getCandidatesCount()));
            } else {
                model.put("candidates", candidateDAO.sortedByPattern(sort, page));
                model.put("pages", getPagesCount(candidateDAO.getCandidatesCount(sort)));
            }
            model.put("page", page);
            model.put("sort", sort);
            return new ModelAndView(INDEX.getAddress(), model);
        } catch (Exception e){
            return getModelError(INDEX.getAddress(), e.toString());
        }
    }


    /**
     * Method returns ModelAndView with incoming page end message.
     * As usual this method serve Exception situations in MainController
     *
     * @param page String
     * @param message String
     * @return ModelAndView
     */
    public ModelAndView getModelError(String page, String message){
        Map<String, Object> model = new HashMap<>();
        model.put(ERROR, message);

        return new ModelAndView(page, model);
    }
}