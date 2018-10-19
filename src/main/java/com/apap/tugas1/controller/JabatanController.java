package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;




@Controller
public class JabatanController {
	
	@Autowired
	private JabatanService jabatanService;
	
	
	//Fungsi untuk menambahkan data jabatan
	@RequestMapping(value="/jabatan/tambah",method=RequestMethod.GET)
	private String tambahJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "tambahJabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah",method=RequestMethod.POST)
	private String tambahJabatanSubmit(@ModelAttribute JabatanModel jabatan,Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan",jabatan);
		return "tambahJabatanSukses";
	}
	
	//Fungsi untuk melihat detil jabatan berdasarkan id
		@RequestMapping(value="/jabatan/view",method=RequestMethod.GET)
		private String lihatPegawai(@RequestParam("idJabatan") String idJabatan, Model model) {
			Long id=Long.parseLong(idJabatan);
			JabatanModel jabatan=jabatanService.getJabatanDetailById(id);
			
		
			model.addAttribute("jabatan",jabatan);			
			
			return "view-jabatan";
		}
		
	//Fungsi untuk mengupdate data suatu jabatan
	
	@RequestMapping(value="/jabatan/ubah",method=RequestMethod.GET)
	private String ubahJabatan(@RequestParam("idJabatan") String idJabatan,Model model) {
		Long id=Long.parseLong(idJabatan);
		JabatanModel jabatan=jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", jabatan);
		return "ubahJabatan";
	}
		
	
	@RequestMapping(value="/jabatan/ubah",method=RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan,Model model) {
		
		jabatanService.ubahJabatan(jabatan);
		model.addAttribute("jabatan",jabatan);
		return "ubahJabatanSukses";
	}
	
	//Fungsi untuk menghapus seorang pilot
	@RequestMapping("/jabatan/hapus")
	public String hapusJabatan(@RequestParam("idJabatan") String idJabatan,Model model ) {
		Long id=Long.parseLong(idJabatan);
		JabatanModel jabatan=jabatanService.getJabatanDetailById(id);
		
		List<PegawaiModel> listPegawai=jabatan.getListPegawai();
		model.addAttribute("jabatan",jabatan);
		
		if(!listPegawai.isEmpty()) {
			return "hapusJabatanError";				
		}
		else {
			
			jabatanService.hapusJabatan(jabatan);
			return "hapusJabatanSukses";
		}
		
		
		
		
		
		
	}
		
	//Fungsi untuk melihat semua jabatan
	@RequestMapping("/jabatan/viewall")
	public String viewallJabatan(Model model) {
		
		List<JabatanModel> listJabatan=jabatanService.getListJabatan();
		
		model.addAttribute("listJabatan",listJabatan);
		return "viewall-jabatan";
	}

}
