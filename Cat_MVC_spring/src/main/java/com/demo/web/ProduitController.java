package com.demo.web;

import java.util.List;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.dao.ProduitRepository;
import com.demo.entities.Produit;

@Controller
public class ProduitController {

	@Autowired
	private ProduitRepository produitRep;

	@RequestMapping(value = "/user/index")
	public String index(Model model,

	@RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "5") int s,
			@RequestParam(name = "mc", defaultValue = "") String mc) {

		Page<Produit> Pageproduits = produitRep.chercher("%" + mc + "%",
				PageRequest.of(p, s));
		model.addAttribute("listProduits", Pageproduits.getContent());
		int[] pages = new int[Pageproduits.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("size", s);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motcle", mc);

		return "produits";
	}

	@RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
	public String delete(Long id, String mc, int page, int size) {

		produitRep.deleteById(id);

		return "redirect:/user/index?page=" + page + "&size=" + size + "&mc=" + mc;
	}

	@RequestMapping(value = "/admin/ajouter", method = RequestMethod.GET)
	public String AjoutProduit(Model model) {
		model.addAttribute("produit", new Produit());
		return "AjoutProduit";
	}

	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public String EditProduit(Model model, Long id) {
		Optional<Produit> p = produitRep.findById(id);
		if (p.isPresent()) {

			model.addAttribute("produit", p.get());

		}
		return "EditProduit";
	}

	@RequestMapping(value = "/admin/save", method = RequestMethod.POST)
	public String Save(Model model, @Validated Produit produit, BindingResult er) {

		if (er.hasErrors())
			return "AjoutProduit";

		produitRep.save(produit);
		return "Confirmation";

	}
	
	@RequestMapping(value="/")
	public String home(){
		return "redirect:/user/index";
	}
	
	@RequestMapping(value="/403")
	public String accesDenied(){
		return "403";
	}

}
