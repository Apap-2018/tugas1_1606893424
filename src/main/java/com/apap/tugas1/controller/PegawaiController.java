package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		
		
		int counter=1;
		model.addAttribute("counter", counter);
			
		model.addAttribute("listProvinsi",provinsiService.getListProvinsi());
		model.addAttribute("listInstansi",instansiService.getListInstansi());
		model.addAttribute("listJabatan",jabatanService.getListJabatan());
		
		return "tambahPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah",params= {"counter"},method=RequestMethod.POST)
	private String tambahDaftarJabatanPegawaiBaru(@ModelAttribute PegawaiModel pegawai, Model model,Integer counter) {
		
		model.addAttribute("listProvinsi",provinsiService.getListProvinsi());
		model.addAttribute("listInstansi",instansiService.getListInstansi());
		model.addAttribute("listJabatan",jabatanService.getListJabatan());
		
		counter++;
		
		model.addAttribute("counter", counter);
		model.addAttribute("pegawai",pegawai);
		
		return "tambahPegawai";
	}
	
	
	
	@RequestMapping(value="/pegawai/tambah",method=RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai,Model model) {
		//fungsi menentukan NIP		
		String tanggalLahir= String.valueOf(pegawai.getTanggal_lahir()).replace("-","");
		
		//mencari no.urut pegawai
		List<PegawaiModel> daftarPegawai=pegawaiService.getListPegawai();
		
		int urutanCounter=1;
		for(int i=0;i<daftarPegawai.size()-1;i++) {
			if(daftarPegawai.get(i).getTanggal_lahir().equals(pegawai.getTanggal_lahir())&&daftarPegawai.get(i).getTahun_masuk().equals(pegawai.getTahun_masuk())) {
				urutanCounter++;
			}
			
		}
		
		String urutanPegawai="";
		if(urutanCounter<10) {
			urutanPegawai="0"+urutanCounter;			
		}
		else {
			urutanPegawai=String.valueOf(urutanCounter);
		}
		
		String nip=pegawai.getInstansi().getId()+tanggalLahir+pegawai.getTahun_masuk()+urutanPegawai;		
		pegawai.setNip(nip);
		
		
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai",pegawai);
		return "tambahPegawaiSukses";
	}
	
	//Fungsi untuk melihat pegawai termuda dan tertua dari suatu instansi
		@RequestMapping(value="/pegawai/termuda-tertua",method=RequestMethod.GET)
		private String lihatPegawaiTermudaTertua(@RequestParam("idInstansi") String idInstansi, Model model) {
					
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
			int counter=pegawai.getListJabatan().size();
			model.addAttribute("counter", counter);
			model.addAttribute("initialCounter", counter);
			return "ubahPegawai";
		}
			
		
		@RequestMapping(value="/pegawai/ubah",method=RequestMethod.POST)
		private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai,Model model) {
			
			
			pegawaiService.ubahPegawai(pegawai);
			model.addAttribute("pegawai",pegawai);
			return "ubahPegawaiSukses";
		}
		
		
		
		@RequestMapping(value="/pegawai/ubah",params= {"counter"},method=RequestMethod.POST)
		private String tambahDaftarJabatan(@ModelAttribute PegawaiModel pegawai, Model model,Integer counter,Integer initialCounter) {
			
			model.addAttribute("listProvinsi",provinsiService.getListProvinsi());
			model.addAttribute("listInstansi",instansiService.getListInstansi());
			model.addAttribute("listJabatan",jabatanService.getListJabatan());
			model.addAttribute("initialCounter", initialCounter);
			counter++;
			
			model.addAttribute("counter", counter);
			model.addAttribute("pegawai",pegawai);
			return "ubahPegawai";
		}
		
		@RequestMapping(value="/pegawai/cari")
		private String cariPegawai(Model model,Long idProvinsi,Long idInstansi,Long idJabatan) {
			
			
			model.addAttribute("listProvinsi",provinsiService.getListProvinsi());
			model.addAttribute("listInstansi",instansiService.getListInstansi());
			model.addAttribute("listJabatan",jabatanService.getListJabatan());
					
			
			if(idProvinsi==null) {
				idProvinsi=(long) 0;
			}
			if(idInstansi==null) {
				idInstansi=(long) 0;
			}
			if(idJabatan==null) {
				idJabatan=(long) 0;
			}
			List<PegawaiModel> filterPegawai=pegawaiService.getListPegawai();
			for(int i=0;i<filterPegawai.size()-1;i++) {
				if(filterPegawai.get(i).getInstansi().getId()!=idInstansi&&idInstansi!=0) {
					filterPegawai.remove(i);
				}
				if(filterPegawai.get(i).getInstansi().getProvinsi().getId()!=idProvinsi&&idProvinsi!=0) {
					filterPegawai.remove(i);
				}
				
				for(int j=0;j<filterPegawai.get(i).getListJabatan().size()-1;j++) {
					if(filterPegawai.get(i).getListJabatan().get(j).getId()!=idJabatan&&idJabatan!=0) {
						filterPegawai.remove(i);
					}					
				}
			}	
			
			
			model.addAttribute("listPegawai",filterPegawai);		
			
			
			return "cariPegawai";
			
			
		}
		
		
		@RequestMapping(value="/pegawai/jumlah")
		private String jumlahPegawai(Model model,Long idJabatan) {
			
			
			
			model.addAttribute("listJabatan",jabatanService.getListJabatan());
			int counterPegawai=0;
			if(idJabatan!=null) {				
				List<PegawaiModel> daftarPegawai=pegawaiService.getListPegawai();
				for(int i=0;i<daftarPegawai.size()-1;i++) {		
					for(int j=0;j<daftarPegawai.get(i).getListJabatan().size()-1;j++) {
						if(daftarPegawai.get(i).getListJabatan().get(j).getId()!=idJabatan&&idJabatan!=0) {
							counterPegawai++;
						}					
					}
				}
				
				JabatanModel jabatan=jabatanService.getJabatanDetailById(idJabatan);
				model.addAttribute("jabatan",jabatan);
				model.addAttribute("jumlah",counterPegawai);	
			}
			
			
				
			
			
			return "jumlahPegawai";
			
			
		}
		
		
		
		
	

}
