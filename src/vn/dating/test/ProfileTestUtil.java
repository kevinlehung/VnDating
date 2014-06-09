package vn.dating.test;

import vn.dating.adapter.ProfileArrayAdapter;
import vn.dating.task.FindProfilesTask;

public class ProfileTestUtil {
	/*public static List<ProfileBean> generateListProfile() {
		List<ProfileBean> profileBeans = new ArrayList<ProfileBean>();
		
		profileBeans.add(new ProfileBean("Albert Einstein", "http://nekneeraj.files.wordpress.com/2012/05/130323101.jpg", "One of the finest scientists of all times"));
		profileBeans.add(new ProfileBean("Elizabeth Taylor", "http://media-cache-ec0.pinimg.com/236x/7e/46/71/7e4671d7f10f218320c2c9aa225f8868.jpg", "A beautifull girl"));
		profileBeans.add(new ProfileBean("Steve Jobs", "http://slodive.com/wp-content/uploads/2012/07/pictures-of-famous-people/steve-jobs.jpg", "The zany and innovative founder of the Apple universe"));
		profileBeans.add(new ProfileBean("Mark Zuckerberg", "http://slodive.com/wp-content/uploads/2012/07/pictures-of-famous-people/mark-zuckerberg.jpg", "The young multi-billionaire and founder of social networking site Facebook"));
		profileBeans.add(new ProfileBean("Nelson Mandela", "http://slodive.com/wp-content/uploads/2012/07/pictures-of-famous-people/nelson-mandela.jpg", "South African champion of freedom"));
		profileBeans.add(new ProfileBean("Bill Gates", "http://slodive.com/wp-content/uploads/2012/07/pictures-of-famous-people/bill-gates.jpg", "The college dropout who changed the history of computers forever"));
		profileBeans.add(new ProfileBean("Dalai Lama", "http://slodive.com/wp-content/uploads/2012/07/pictures-of-famous-people/dalai-lama.jpg", "Happy Picture"));
		profileBeans.add(new ProfileBean("Abdul Kalam", "http://slodive.com/wp-content/uploads/2012/07/pictures-of-famous-people/abdul-kalam.jpg", "India’s former President and famous scientist"));
		profileBeans.add(new ProfileBean("Marilyn Monroe", "http://slodive.com/wp-content/uploads/2012/05/marilyn-monroe-pictures/old-photographed.jpg", "The golden diva"));
		profileBeans.add(new ProfileBean("Barrack Obama", "http://slodive.com/wp-content/uploads/2012/07/pictures-of-famous-people/barack-obama.jpg", "American President and the country’s first Black President"));
		return profileBeans;
	}*/
	
	public static void generateListProfile(ProfileArrayAdapter adapter) {
		FindProfilesTask task = new FindProfilesTask(adapter);
		task.execute();
	}
}
