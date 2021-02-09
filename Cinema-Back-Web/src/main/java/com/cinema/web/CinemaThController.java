package com.cinema.web;

import java.nio.file.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cinema.dao.CategorieRepository;
import com.cinema.dao.CinemaRepository;
import com.cinema.dao.FilmRepository;
import com.cinema.dao.PlaceRepository;
import com.cinema.dao.ProjectionRepository;
import com.cinema.dao.SalleRepository;
import com.cinema.dao.SeanceRepository;
import com.cinema.dao.TicketRepository;
import com.cinema.dao.VilleRepository;
import com.cinema.entities.Categorie;
import com.cinema.entities.Cinema;
import com.cinema.entities.Film;
import com.cinema.entities.Place;
import com.cinema.entities.Projection;
import com.cinema.entities.Salle;
import com.cinema.entities.Seance;
import com.cinema.entities.Ticket;
import com.cinema.entities.Ville;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class CinemaThController {

	@Data  @NoArgsConstructor
	public class Salles {
		private ArrayList<Salle> salles;
	
		// default and parameterized constructor
	
		public void addSalle(Salle salle) {
			this.salles.add(salle);
		}
		
		// getter and setter
	}
	

		@Autowired
		private VilleRepository villeRepository;
		@Autowired
		private CinemaRepository cinemaRepository;
		@Autowired
		private SalleRepository salleRepository;
		@Autowired
		private PlaceRepository placeRepository;
		@Autowired
		private FilmRepository filmRepository;
		@Autowired
		private SeanceRepository seanceRepository;
		@Autowired
		private TicketRepository ticketRepository;
		@Autowired
		private CategorieRepository categorieRepository;
		@Autowired
		private ProjectionRepository projectionRepository; 
		
		/*
		 * ------------------------------------------------------------------ Ville ------------------------------------------------
		 */
		
		@GetMapping(path = "/ville")
		public String villes(Model model,
				@RequestParam(name="page",defaultValue = "0")int page,
				@RequestParam(name="size",defaultValue = "4")int size,
				@RequestParam(name="keyword",defaultValue = "")String mc) {
			Page<Ville> pageVilles= villeRepository.findByNameContains(mc, PageRequest.of(page, size));
			model.addAttribute("villes",pageVilles.getContent());
			model.addAttribute("pages",new int[pageVilles.getTotalPages()]);
			model.addAttribute("currentPage", page);
			model.addAttribute("size", size);
			model.addAttribute("keyword", mc);
			return "ville";
		}
		
		
		@GetMapping(path = "/deleteVille")
		public String delete(Long id,String keyword,int page,int size) {
			villeRepository.deleteById(id);
			return "redirect:/ville?page="+page+"&size="+size+"&keyword="+keyword;
		}
		
		@GetMapping(path = "/editVille")
		public String editVille(Model model, Long id) {
			Ville v=villeRepository.findById(id).get();
			model.addAttribute("ville", v);
			model.addAttribute("mode", "edit");
			return "formVille";
		}
	
		@GetMapping(path = "/formVille")
		public String formVille(Model model) {
			model.addAttribute("ville", new Ville());
			model.addAttribute("mode", "new");
			return "formVille";
		}
		
		@PostMapping(path ="/saveVille" )
		public String saveVille(@Valid Ville ville, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return "formVille";
			villeRepository.save(ville);
			return "formVille";
		}
		
		
		/*
		 * ------------------------------------------------------------------ CINEMA ------------------------------------------------
		 */
		
		@GetMapping(path = "/cinema")
		public String cinemas(Model model,
				@RequestParam(name="page",defaultValue = "0")int page,
				@RequestParam(name="size",defaultValue = "4")int size,
				@RequestParam(name="keyword",defaultValue = "")String mc) {
			Page<Cinema> pageCinemas= cinemaRepository.findByNameContains(mc, PageRequest.of(page, size));
			model.addAttribute("cinemas",pageCinemas.getContent());
			model.addAttribute("pages",new int[pageCinemas.getTotalPages()]);
			model.addAttribute("currentPage", page);
			model.addAttribute("size", size);
			model.addAttribute("keyword", mc);
			return "cinema";
		}
		
		@GetMapping(path = "/deleteCinema")
		public String deletecinema(Long id,String keyword,int page,int size) {
			cinemaRepository.deleteById(id);
			return "redirect:/cinema?page="+page+"&size="+size+"&keyword="+keyword;
	}
	
	
	
		@GetMapping(path = "/formCinema")
		public String formCinema(Model model) {
			model.addAttribute("cinema", new Cinema());
			model.addAttribute("mode", "new");
			return "formCinema";
		}

		@GetMapping(path = "/addCinema")
		public String addCinema(Model model) {
			List<Ville> villes = villeRepository.findAll();
			model.addAttribute("villes", villes);		
			model.addAttribute("cinema", new Cinema());
			model.addAttribute("mode", "new");
			return "formAddCinema";
		}
		
		@PostMapping(path ="/saveCinema" )
		public String saveCinema(@Valid Cinema cinema, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return "formCinema";
			cinemaRepository.save(cinema);
			return "formCinema";
		}

		@PostMapping(path ="/addSalles" )
		public String addSalles(@Valid Cinema cinema,BindingResult bindingResult, Model model) {
			if(bindingResult.hasErrors()) return "formCinema";
			cinemaRepository.save(cinema);
			ArrayList<Salle> s=  new ArrayList<Salle>();
			Salle salle = new Salle();

			for (int i = 1; i <= cinema.getNombreSalles(); i++) {
				salle.setCinema(cinema);
				s.add(salle);
				
			}
			Salles salles = new Salles();
			salles.setSalles(s);
			model.addAttribute("salles", salles);
			

			return "addSalles";
		}

/* 		@PostMapping(path ="/saveSalle" )
		public String saveSalle(@Valid Salle salle, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return "formSalle";
			salleRepository.save(salle);
			return "formSalle";
		}
 */

		@GetMapping(path = "/add_____________Salles")
		public String add___Salles(Model model, Long id) {
			Cinema v=cinemaRepository.findById(id).get();
			model.addAttribute("cinema", v);
			model.addAttribute("mode", "edit");
			return "formCinema";
		}
		
		@GetMapping(path = "/editCinema")
		public String editCinema(Model model, Long id) {
			Cinema v=cinemaRepository.findById(id).get();
			model.addAttribute("cinema", v);
			model.addAttribute("mode", "edit");
			List<Ville> villes = villeRepository.findAll();
			model.addAttribute("villes", villes);		
			return "formCinema";
		}
		
		/*
		 * ------------------------------------------------------------------ SALLE ------------------------------------------------
		 */
		
		@GetMapping(path = "/salle")
		public String salles(Model model,
				@RequestParam(name="page",defaultValue = "0")int page,
				@RequestParam(name="size",defaultValue = "10")int size,
				@RequestParam(name="keyword",defaultValue = "")String mc) {
			Page<Salle> pageSalle= salleRepository.findByNameContains(mc, PageRequest.of(page, size));
			model.addAttribute("salles",pageSalle.getContent());
			model.addAttribute("pages",new int[pageSalle.getTotalPages()]);
			model.addAttribute("currentPage", page);
			model.addAttribute("size", size);
			model.addAttribute("keyword", mc);
			return "salle";
		}

		@GetMapping(path = "/sallesCinema")
		public String sallesCinema(Model model, Long id ,
		@RequestParam(name="page",defaultValue = "0")int page,
		@RequestParam(name="size",defaultValue = "10")int size,
		@RequestParam(name="keyword",defaultValue = "")String mc) {	
			Page<Salle> pageSalle= salleRepository.findByIdCinema(id, PageRequest.of(0, size));
			model.addAttribute("salles",pageSalle.getContent());
			model.addAttribute("pages",new int[pageSalle.getTotalPages()]);
			return "salle";
		}
		

		@GetMapping(path = "/deleteSalle")
		public String deletesalle(Long id,String keyword,int page,int size) {
			salleRepository.deleteById(id);
			return "redirect:/salle?page="+page+"&size="+size+"&keyword="+keyword;
	}
	
		@GetMapping(path = "/formSalle")
		public String formSalle(Model model) {
			model.addAttribute("salle", new Salle());
			model.addAttribute("mode", "new");
			return "formSalle";
		}

		@PostMapping("/saveSalles")
		public String saveSalles( Salles salles, Model model) {
			salles.getSalles().forEach((salle) -> salleRepository.save(salle));
			

    		return "redirect:/salle";
		}
		
		@PostMapping(path ="/saveSalle" )
		public String saveSalle(@Valid Salle salle, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return "formSalle";
			salleRepository.save(salle);
			return "formSalle";
		}
		
		@GetMapping(path = "/editSalle")
		public String editSalle(Model model, Long id) {
			Salle v=salleRepository.findById(id).get();
			model.addAttribute("salle", v);
			model.addAttribute("mode", "edit");
			return "formSalle";
		}
	
		/*
		 * ------------------------------------------------------------------ Place ------------------------------------------------
		 */
		
		@GetMapping(path = "/place")
		public String places(Model model,
				@RequestParam(name="page",defaultValue = "0")int page,
				@RequestParam(name="size",defaultValue = "26")int size,
				@RequestParam(name="keyword",defaultValue = "")String mc) {
			Page<Place> pagePlace= placeRepository.findAll(PageRequest.of(page, size));
			model.addAttribute("places",pagePlace.getContent());
			model.addAttribute("pages",new int[pagePlace.getTotalPages()]);
			model.addAttribute("currentPage", page);
			model.addAttribute("size", size);
			model.addAttribute("keyword", mc);
			return "place";
		}
		
		@GetMapping(path = "/deletePlace")
		public String deleteplace(Long id,String keyword,int page,int size) {
			placeRepository.deleteById(id);
			return "redirect:/place?page="+page+"&size="+size+"&keyword="+keyword;
	}
		@GetMapping(path = "/formPlace")
		public String formPlace(Model model) {
			model.addAttribute("place", new Place());
			model.addAttribute("mode", "new");
			return "formPlace";
		}
		
		@PostMapping(path ="/savePlace" )
		public String savePlace(@Valid Place place, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return "formPlace";
			placeRepository.save(place);
			return "formPlace";
		}
		
		@GetMapping(path = "/editPlace")
		public String editPlace(Model model, Long id) {
			Place v=placeRepository.findById(id).get();
			model.addAttribute("place", v);
			model.addAttribute("mode", "edit");
			return "formPlace";
		}
		/*
		 * ------------------------------------------------------------------ Film ------------------------------------------------
		 */
		
		@GetMapping(path = "/film")
		public String films(Model model,
				@RequestParam(name="page",defaultValue = "0")int page,
				@RequestParam(name="size",defaultValue = "4")int size,
				@RequestParam(name="keyword",defaultValue = "")String mc) {
			Page<Film> pageFilm= filmRepository.findByTitreContains(mc, PageRequest.of(page, size));
			model.addAttribute("films",pageFilm.getContent());
			model.addAttribute("pages",new int[pageFilm.getTotalPages()]);
			model.addAttribute("currentPage", page);
			model.addAttribute("size", size);
			model.addAttribute("keyword", mc);
			return "film";
		}
		
		@GetMapping(path = "/deleteFilm")
		public String deletefilm(Long id,String keyword,int page,int size) {
			filmRepository.deleteById(id);
			return "redirect:/film?page="+page+"&size="+size+"&keyword="+keyword;
	}
		@GetMapping(path = "/formFilm")
		public String formFilm(Model model) {
			model.addAttribute("film", new Film());
			model.addAttribute("mode", "new");
			model.addAttribute("categories", categorieRepository.findAll());
			return "formFilm";
		}
		
		@PostMapping(path ="/saveFilm" )
		public String saveFilm(@Valid Film film, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Model model){
			String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/images";
			model.addAttribute("categories", categorieRepository.findAll());
			if(bindingResult.hasErrors()) return "formFilm";
			StringBuilder fileName = new StringBuilder();
			Path filePath = Paths.get(uploadDirectory, file.getOriginalFilename());
			fileName.append(file.getOriginalFilename());
			if (!file.isEmpty()){
				try {
					Files.write(filePath, file.getBytes());

				} catch (Exception e) {
					e.printStackTrace();
				}
				film.setPhoto(file.getOriginalFilename());
			}
			filmRepository.save(film);
			return "formFilm";
		}
		
		@GetMapping(path = "/editFilm")
		public String editFilm(Model model, Long id) {
			Film v=filmRepository.findById(id).get();
			model.addAttribute("categories", categorieRepository.findAll());
			model.addAttribute("film", v);
			model.addAttribute("mode", "edit");
			return "formFilm";
		}
		
		/*
		 * --------------------------------------------------------------------SEANCE------------------------------------------------------
		 */		
		
		@GetMapping(path = "/seance")
		public String seances(Model model,
				@RequestParam(name="page",defaultValue = "0")int page,
				@RequestParam(name="size",defaultValue = "26")int size,
				@RequestParam(name="keyword",defaultValue = "")String mc) {
			Page<Seance> pageSeance= seanceRepository.findAll(PageRequest.of(page, size));
			model.addAttribute("seances",pageSeance.getContent());
			model.addAttribute("pages",new int[pageSeance.getTotalPages()]);
			model.addAttribute("currentPage", page);
			model.addAttribute("size", size);
			model.addAttribute("keyword", mc);
			return "seance";
		}
		
		@GetMapping(path = "/deleteSeance")
		public String deleteseance(Long id,String keyword,int page,int size) {
			seanceRepository.deleteById(id);
			return "redirect:/seance?page="+page+"&size="+size+"&keyword="+keyword;
	}
		
		
		@GetMapping(path = "/formSeance")
		public String formSeance(Model model) {
			model.addAttribute("seance", new Seance());
			model.addAttribute("mode", "new");
			return "formSeance";
		}
		
		@PostMapping(path ="/saveSeance" )
		public String saveSeance(@Valid Seance seance, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return "formSeance";
			seanceRepository.save(seance);
			return "formSeance";
		}
		
		@GetMapping(path = "/editSeance")
		public String editSeance(Model model, Long id) {
			Seance v=seanceRepository.findById(id).get();
			model.addAttribute("seance", v);
			model.addAttribute("mode", "edit");
			return "formSeance";
		}
		
		/*
		 * ---------------------------------TICKET-----------------------------------------------------------------------------------
		 */		
		
		
		@GetMapping(path = "/ticket")
		public String tickets(Model model,
				@RequestParam(name="page",defaultValue = "0")int page,
				@RequestParam(name="size",defaultValue = "100")int size,
				@RequestParam(name="keyword",defaultValue = "")String mc) {
			Page<Ticket> pageTickets= ticketRepository.findAll(PageRequest.of(page, size));
			model.addAttribute("totalTickets", pageTickets.getNumberOfElements());
			model.addAttribute("tickets",pageTickets.getContent());
			model.addAttribute("pages",new int[pageTickets.getTotalPages()]);
			model.addAttribute("currentPage", page);
			model.addAttribute("size", size);
			model.addAttribute("keyword", mc);
			return "ticket";
		}
		
		
		@GetMapping(path = "/deleteTicket")
		public String deleteticket(Long id,String keyword,int page,int size) {
			ticketRepository.deleteById(id);
			return "redirect:/ticket?page="+page+"&size="+size+"&keyword="+keyword;
		}
		
		@GetMapping(path = "/formTicket")
		public String formTicket(Model model) {
			model.addAttribute("ticket", new Ticket());
			model.addAttribute("mode", "new");
			return "formTicket";
		}
		
		@PostMapping(path ="/saveTicket" )
		public String saveTicket(@Valid Ticket ticket, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return "formTicket";
			ticketRepository.save(ticket);
			return "formTicket";
		}
		
		@GetMapping(path = "/editTicket")
		public String editTicket(Model model, Long id) {
			Ticket v=ticketRepository.findById(id).get();
			model.addAttribute("tickt", v);
			model.addAttribute("mode", "edit");
			return "formTicket";
		}
		
		
		/*
		 * ------------------------------------------------------------------ Categorie ------------------------------------------------
		 */
		
		
		@GetMapping(path = "/categorie")
		public String categories(Model model,
				@RequestParam(name="page",defaultValue = "0")int page,
				@RequestParam(name="size",defaultValue = "4")int size,
				@RequestParam(name="keyword",defaultValue = "")String mc) {
			Page<Categorie> pageCategorie= categorieRepository.findByNameContains(mc, PageRequest.of(page, size));
			model.addAttribute("categories",pageCategorie.getContent());
			model.addAttribute("pages",new int[pageCategorie.getTotalPages()]);
			model.addAttribute("currentPage", page);
			model.addAttribute("size", size);
			model.addAttribute("keyword", mc);
			return "categorie";
		}
		
		@GetMapping(path = "/deleteCategorie")
		public String deletecategorie(Long id,String keyword,int page,int size) {
			categorieRepository.deleteById(id);
			return "redirect:/categorie?page="+page+"&size="+size+"&keyword="+keyword;
	}
	
		@GetMapping(path = "/formCategorie")
		public String formCategorie(Model model) {
			model.addAttribute("categorie", new Categorie());
			model.addAttribute("mode", "new");
			return "formCategorie";
		}
		
		@PostMapping(path ="/saveCategorie" )
		public String saveCategorie(@Valid Categorie categorie, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return "formCategorie";
			categorieRepository.save(categorie);
			return "formCategorie";
		}
		
		@GetMapping(path = "/editCategorie")
		public String editCategorie(Model model, Long id) {
			Categorie v=categorieRepository.findById(id).get();
			model.addAttribute("categorie", v);
			model.addAttribute("mode", "edit");
			return "formCategorie";
		}
		
	
		/*
		 * ------------------------------------------------------------------ Projection ------------------------------------------------
		 */
	
	
	  @GetMapping(path = "/projection") 
	  public String projections(Model model,
			  @RequestParam(name="page",defaultValue = "0")int page,
			  @RequestParam(name="size",defaultValue = "75")int size,
			  @RequestParam(name="keyword",defaultValue = "")String mc) {
		 Page<Projection> pageProjection= projectionRepository.findAll(PageRequest.of(page, size));
		 model.addAttribute("projections",pageProjection.getContent());
		 model.addAttribute("pages",new int[pageProjection.getTotalPages()]);
		 model.addAttribute("currentPage", page); 
		 model.addAttribute("size", size);
		 model.addAttribute("keyword", mc);
		 return "projection"; 
	}
	  
	  @GetMapping(path = "/deleteProjection") 
	  public String deleteprojection(Long id,String keyword,int page,int size) { 
		  projectionRepository.deleteById(id);
	  return "redirect:/projection?page="+page+"&size="+size+"&keyword="+keyword; }
	  
		@GetMapping(path = "/formProjectionVille")
		public String formProjection(Model model) {
			model.addAttribute("films", filmRepository.findAll());
			model.addAttribute("villes", villeRepository.findAll() );
			model.addAttribute("seances", seanceRepository.findAll());
			model.addAttribute("projection", new Projection());
			model.addAttribute("mode", "new");
			model.addAttribute("state", "ville");
			return "formProjection";
		}
		
		@PostMapping(path ="/saveProjectionVille" )
		public String saveProjectionVille(@Valid Ville ville, BindingResult bindingResult, Model model) {
			if(bindingResult.hasErrors()) return "formProjectionVille";
			//projectionRepository.save(projection);
			model.addAttribute("state", "cinema");
			model.addAttribute("currentVille", ville);
			model.addAttribute("cinemas", cinemaRepository.findByIdVille(ville.getId()));
			
			return "formProjection";
		}

		@PostMapping(path ="/saveProjectionCinema" )
		public String saveProjectionCinema(@Valid Cinema cinema, BindingResult bindingResult, Model model) {
			if(bindingResult.hasErrors()) return "formProjectionVille";
			//projectionRepository.save(projection);
			model.addAttribute("state", "final");
			model.addAttribute("films", filmRepository.findAll());
			model.addAttribute("seances", seanceRepository.findAll());
			model.addAttribute("salles", salleRepository.findByIdCinema(cinema.getId(), PageRequest.of(0, 200)));
			model.addAttribute("projection", new Projection());

			return "formProjection";
		}
		
		@PostMapping(path ="/saveProjection" )
		public String saveProjection(@Valid Projection projection, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return "formProjection";
			projectionRepository.save(projection);
			return "redirect:/projection";
		}

		
		@GetMapping(path = "/editProjection")
		public String editProjection(Model model, Long id) {
			Projection v=projectionRepository.findById(id).get();
			model.addAttribute("films", filmRepository.findAll());
			model.addAttribute("villes", villeRepository.findAll() );
			model.addAttribute("projection", v);
			model.addAttribute("mode", "edit");
			return "formProjection";
		}

		
	@GetMapping(path = "/index")
	public String index() {
		return "index";
	}

}
