package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/com_company")
public class MainController {

    private static final String SKILL_PARAM = "skill_";
    private static final String RATE_PARAM = "rate";
    private static final int MAX_SKILLS = 10;

    @Autowired
    private CandidateDAO candidateDAO;

	@RequestMapping("/")
	public ModelAndView candidatesList() {
        return new ModelAndView("index", "candidates", candidateDAO.sortedByDate());
	}

    @RequestMapping("/index")
    public String indexTest() {
        return "redirect: /";
    }

    @RequestMapping("/sortByName")
    public ModelAndView candidatesListByName() {
        return new ModelAndView("index", "candidates", candidateDAO.sortedByName());
    }

    @RequestMapping("/add")
    public ModelAndView addTest() {
        return new ModelAndView ("add");
    }

/*	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(value="pattern") String pattern) {
		return new ModelAndView("index", "advs", advDAO.list(pattern));
	}
*/
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam(value="id") int id) {
        candidateDAO.delete(id);
        return new ModelAndView("index", "candidates", candidateDAO.sortedByDate());
	}

    @RequestMapping("/candidateInfo")
    public ModelAndView info(@RequestParam(value="id") int id) {
        return new ModelAndView("candidateInfo", "candidate", candidateDAO.getById(id));
    }

    @RequestMapping(value = "/addCandidate", method = RequestMethod.POST)
    public ModelAndView addInfoTest(@RequestParam Map<String,String> params)
    {
        // adding user INFO
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(params.get("interviewDate"));
        } catch (ParseException e) { e.getClass();}
        Candidate c = new Candidate(params.get("firstName"), params.get("lastName"), date);
        candidateDAO.add(c);

        // adding contacts
        if(params.containsKey("telephone") && !params.get("telephone").equals("")){
            candidateDAO.addContact(new Contact(c, "telephone", params.get("telephone")));}
        if(params.containsKey("mail") && !params.get("mail").equals("")){
            candidateDAO.addContact(new Contact(c, "mail", params.get("mail")));}
        if(params.containsKey("skype") && !params.get("skype").equals("")){
            candidateDAO.addContact(new Contact(c, "skype", params.get("skype")));}
        if(params.containsKey("other1") && !params.get("other1").equals("")){
            candidateDAO.addContact(new Contact(c, "other1", params.get("contact1")));}
        if(params.containsKey("other2") && !params.get("other2").equals("")){
            candidateDAO.addContact(new Contact(c, "other2", params.get("contact2")));}

        // adding skills
        for(int i=1; i<=MAX_SKILLS; i++){
            String temp = SKILL_PARAM + i;
            if(params.containsKey(temp) && !params.get(temp).equals("")){
                candidateDAO.addSkill(new Skill(c, params.get(temp), Integer.parseInt(params.get(RATE_PARAM + i))));
            }
        }

        return new ModelAndView("index", "candidates", candidateDAO.sortedByDate());
    }
}