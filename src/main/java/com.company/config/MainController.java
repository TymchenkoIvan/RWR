package com.company.config;

import com.company.dao.CandidateDAO;
import com.company.dao.ContactDAO;
import com.company.dao.SkillDAO;
import com.company.entities.Candidate;
import com.company.exception.MyApplicationException;
import org.apache.log4j.Logger;
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
@RequestMapping("/")
public class MainController {

    @Autowired
    private CandidateDAO candidateDAO;

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private SkillDAO skillDAO;

    private static final Logger logger = Logger.getLogger(MainController.class);

    //vars user in form
    public static final String SKILL_PARAM_PATTERN = "skill_";
    public static final String SKILL_RATE_PATTERN = "rate";
    public static final String CONTACT_MAIL_PATTERN = "mail";
    public static final String CONTACT_TYPE_PATTERN = "type";
    public static final String CONTACT_VALUE_PATTERN = "value";
    //vars used in .jsp pages
    public static final String ERROR_VAR = "error";
    public static final String PAGES_VAR = "pages";
    public static final String PAGE_VAR = "page";
    public static final String SORT_VAR = "sort";
    public static final String CANDIDATE_VAR = "candidates";
    //important settings used in GUI
    public static final int MAX_SKILLS = 10;
    public static final int MAX_CONTACTS = 4;
    public static final int MAX_CANDIDATES_ON_PAGE = 10;


    /**
     * Method sorts List<Candidate> by Date using candidateDAO.sortByDate(), and returns index page with paging number 1.
     * It ia default sorting.
     *
     * @return ModelAndView
     */
    @RequestMapping("/")
    public ModelAndView candidatesList() {
        logger.info("request:/");
        return getModel(BY_DATE.name(), 1);
    }


    /**
     * Method returns ModelAndView with List<Candidate>, number of page, and pages amount.
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    public ModelAndView paging(@RequestParam(value=PAGE_VAR) int page,
                               @RequestParam(value=SORT_VAR) String sort) {
        logger.info("request:/paging");
        return getModel(sort, page);
    }


    /**
     * Method returns index page sorted by name with paging number 1.
     *
     * @return ModelAndView
     */
    @RequestMapping("/sortByName")
    public ModelAndView candidatesListByName() {
        logger.info("request:/sortByName");
        return getModel(BY_NAME.name(), 1);
    }


    /**
     * Method redirects to page where user can add new candidate.
     *
     * @return ModelAndView
     */
    @RequestMapping("/add")
    public ModelAndView add() {
        logger.info("request:/add");
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
        logger.info("request:/search");
        return getModel(pattern, 1);
	}


    /**
     * GET part of PRG pattern with (value = "/delete", method = RequestMethod.POST)
     *
     * @return
     */
    @RequestMapping(value = "/deleteError", method = RequestMethod.GET)
    public ModelAndView deleteGetError(){
        logger.info("request:/deleteError");
        return getModelError(INFO.getAddress(), "Wrong");
    }


    /**
     * GET part of PRG pattern with (value = "/delete", method = RequestMethod.POST)
     *
     * @return
     */
    @RequestMapping(value = "/deleted", method = RequestMethod.GET)
    public ModelAndView deleteGet(){
        logger.info("request:/deleted");
        return getModel(BY_DATE.name(), 1);
    }


    /**
     * Deleting candidate by id from database. Also will be deleted all entities that aggregated and mapped by
     * Candidate(Skill and Contact), because cascade = CascadeType.ALL.
     * Method returns index page sorted by date with paging number 1.
     * If method will catch Exception it will return page with error message.
     * Uses PRG pattern and redirects to (value = "/deleted", method = RequestMethod.GET) if ok
     * Uses PRG pattern and redirects to (value = "/deleteError", method = RequestMethod.GET) if Exception
     *
     * @param id int
     * @return ModelAndView
     */
	@RequestMapping("/delete")
	public String deletePost(@RequestParam(value="id") int id) {
        logger.info("request:/delete");
        try {
            candidateDAO.delete(id);
            logger.info("user deleted" +id);
        } catch (MyApplicationException e) {
            logger.error("can't delete user" +id);
            return "redirect:/deleteError";
        }
        return "redirect:/deleted";
	}


    /**
     * Method redirects to page where user can find all info about candidate by his id.
     *
     * @param id int
     * @return ModelAndView
     */
    @RequestMapping("/candidateInfo")
    public ModelAndView info(@RequestParam(value="id") int id) {
        logger.info("request:/candidateInfo" + id);
        Map<String, Object> model = new HashMap<>();
        model.put(CANDIDATE_VAR, candidateDAO.getById(id));
        return new ModelAndView(INFO.getAddress(), model);
    }


    /**
     * GET part of PRG pattern with (value = "/addCandidate", method = RequestMethod.POST)
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/addCandidate", method = RequestMethod.GET)
    public ModelAndView addCandidateGet(){
        logger.info("request:/addCandidate");
        return getModel(BY_DATE.name(), 1);
    }

    /**
     * GET part of PRG pattern with (value = "/addCandidate", method = RequestMethod.POST)
     * It will be redirected if mail is wrong.
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/addCandidateMailError", method = RequestMethod.GET)
    public ModelAndView addCandidateGetMailError(){
        logger.info("request:/addCandidateMailError");
        return getModelError(ADD.getAddress(), "Wrong mail");
    }


    /**
     * This method add all information about new candidate from huge form.
     * If method can not create new candidate or add his mail will return this page(..add.jsp) with error message.
     * Uses PRG pattern and redirects to (value = "/addCandidate", method = RequestMethod.GET)
     * If mail wrong will be redirected to "/addCandidateMailError".
     *
     * All contacts(excepts MAIL) and skills can be empty.
     *
     * @param params Map <String,String>
     * @return redirect to GET
     */
    @RequestMapping(value = "/addCandidate", method = RequestMethod.POST)
    public String addCandidatePost(@RequestParam Map<String,String> params){
        // adding user and mandatory contacts
        Candidate c = null;
        try {
            c = candidateDAO.addAndGet(params.get("firstName"), params.get("lastName"), params.get("interviewDate"));
            if(contactDAO.isMailReal(params.get(CONTACT_MAIL_PATTERN))) {
                logger.info("tries to create new candidate");
                contactDAO.add(c, CONTACT_MAIL_PATTERN, params.get(CONTACT_MAIL_PATTERN));
            } else {
                logger.info("tries to delete candidate, because mail is wrong");
                candidateDAO.delete(c.getId());
                throw new MyApplicationException();
            }
        } catch (MyApplicationException e) {
            logger.error("can't create new candidate", e);
            return "redirect:/addCandidateMailError";
        }
        // adding not mandatory contacts
        logger.info("contacts adding");
        for(int i=1; i<=MAX_CONTACTS; i++){
            String type = CONTACT_TYPE_PATTERN + i;
            String value = CONTACT_VALUE_PATTERN+ i;
            if(params.containsKey(type) && params.containsKey(value)) {
                contactDAO.add(c, params.get(type), params.get(value));
            }
        }
        // adding skills
        logger.info("skills adding");
        for(int i=1; i<=MAX_SKILLS; i++){
            String skill = SKILL_PARAM_PATTERN + i;
            String rate = SKILL_RATE_PATTERN + i;
            if(params.containsKey(skill) && params.containsKey(rate)) {
                skillDAO.add(c, params.get(skill), params.get(rate));
            }
        }
        return "redirect:/addCandidate";
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
     *
     * @param sort Spring shows type of sorting
     * @param page int
     * @return ModelAndView
     */
    public ModelAndView getModel(String sort, int page){
        Map<String, Object> model = new HashMap<>();
        if (BY_NAME.name().equals(sort)) {
            model.put(CANDIDATE_VAR, candidateDAO.sortedByName(page));
            model.put(PAGES_VAR, getPagesCount(candidateDAO.getCandidatesCount()));
        } else if (BY_DATE.name().equals(sort)) {
            model.put(CANDIDATE_VAR, candidateDAO.sortedByDate(page));
            model.put(PAGES_VAR, getPagesCount(candidateDAO.getCandidatesCount()));
        } else {
            model.put(CANDIDATE_VAR, candidateDAO.sortedByPattern(sort, page));
            model.put(PAGES_VAR, getPagesCount(candidateDAO.getCandidatesCount(sort)));
        }
        model.put(PAGE_VAR, page);
        model.put(SORT_VAR, sort);
        logger.info("model built SUCCESSFULLY");
        return new ModelAndView(INDEX.getAddress(), model);
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
        model.put(ERROR_VAR, message);
        logger.info("ERROR model built SUCCESSFULLY");
        return new ModelAndView(page, model);
    }
}