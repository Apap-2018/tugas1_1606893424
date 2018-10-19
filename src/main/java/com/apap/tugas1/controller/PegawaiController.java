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
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		
		model.addAttribute("listJabatan",jabatanService.getListJabatan());
		
		model.addAttribute("listPegawai",pegawaiService.getListPegawai());
		return "home";
	}
	
	//Fungsi untuk melihat pegawai berdasarkan NIP
	@RequestMapping(value="/pegawai",method=RequestMethod.GET)
	private String lihatPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		
		int gaji=pegawaiService.calculateGaji(nip);
		
		model.addAttribute("pegawai",pegawai);
		model.addAttribute("gaji",gaji);			
		
		return "view-pegawai";
	}
	
	//Fungsi untuk menambahkan data pegawai
	@RequestMapping(value="/pegawai/tambah",method=RequestMethod.GET)
	private String tambahPegawai(Model model) {
		model.addAttribute("pegawai", new PegawaiModel());
		return "tambahPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah",method=RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai) {
		pegawaiService.addPegawai(pegawai);
		return "tambahPegawaiSukses";
	}
	
	//Fungsi untuk melihat pegawai termuda dan tertua dari suatu instansi
		@RequestMapping(value="/pegawai/termuda-tertua",method=RequestMethod.GET)
		private String lihatPegawaiTermudaTertua(@RequestParam("idInstansi") String idInstansi, Model model) {
			//PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
			//int gaji=pegawaiService.calculateGaji(nip);
			//model.addAttribute("pegawai",pegawai);
			//model.addAttribute("gaji",gaji);			
			List<PegawaiModel> listPegawaiInstansi=pegawaiService.getPegawaiDetailByIdInstansi(idInstansi);
			PegawaiModel pegawaiTermuda=pegawaiService.cariPegawaiTermuda(listPegawaiInstansi);
			PegawaiModel pegawaiTertua=pegawaiService.cariPegawaiTertua(listPegawaiInstansi);
			
			model.addAttribute("pegawaiTermuda",pegawaiTermuda);
			model.addAttribute("pegawaiTertua",pegawaiTertua);
			
			int gajiTermuda=pegawaiService.calculateGaji(pegawaiTermuda.getNip());
			model.addAttribute("gajiTermuda",gajiTermuda);
			int gajiTertua=pegawaiService.calculateGaji(pegawaiTertua.getNip());
			model.addAttribute("gajiTertua",gajiTertua);
			
			
			return "view-termuda-tertua";
		}
		
		
	
		//Fungsi untuk mengupdate data seorang pegawai
		
		@RequestMapping(value="/pegawai/ubah",method=RequestMethod.GET)
		private String ubahPegawai(@RequestParam("nip") String nip,Model model) {
			model.addAttribute("listProvinsi",provinsiService.getListProvinsi());
			model.addAttribute("listInstansi",instansiService.getListInstansi());
			model.addAttribute("listJabatan",jabatanService.getListJabatan());
					
			PegawaiModel pegawai=pegawaiService.getPegawaiDetailByNip(nip);
			model.addAttribute("pegawai", pegawai);
			return "ubahPegawai";
		}
			
		
		@RequestMapping(value="/pegawai/ubah",method=RequestMethod.POST)
		private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai,Model model) {
			
			//jabatanService.ubahJabatan(jabatan);
			//model.addAttribute("jabatan",jabatan);
			//return "ubahJabatanSukses";
			
			pegawaiService.ubahPegawai(pegawai);
			model.addAttribute("pegawai",pegawai);
			return "ubahPegawaiSukses";
		}
	

}
