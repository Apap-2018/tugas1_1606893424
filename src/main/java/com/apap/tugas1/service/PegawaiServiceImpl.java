package com.apap.tugas1.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	private List<PegawaiModel> listPegawai;
	
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByNip(nip);
	}
	
	

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		
	}

	
	

	@Override
	public PegawaiModel getPegawaiDetailById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	



	@Override
	public int calculateGaji(String nip) {
		// TODO Auto-generated method stub
		PegawaiModel pegawai = pegawaiDb.findByNip(nip);
		
		//mencari gaji pokok tertinggi , terutama jika jabatan lebih dari satu
		double gajiPokok=0;
		for(int i=0;i<pegawai.getListJabatan().size();i++) {
			if(gajiPokok<pegawai.getListJabatan().get(i).getGaji_pokok()) {
				gajiPokok=pegawai.getListJabatan().get(i).getGaji_pokok();				
			}
		}
		
		double presentaseTunjangan=pegawai.getInstansi().getProvinsi().getPresentase_tunjangan();
		
		double gaji=gajiPokok+(presentaseTunjangan/100*gajiPokok);
		
		return (int)gaji;
		
		
	}



	@Override
	public List<PegawaiModel> getListPegawai() {
		// TODO Auto-generated method stub
		return pegawaiDb.findAll();
	}



	@Override
	public void hapusPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void ubahPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		PegawaiModel updatedPegawai=pegawaiDb.findByNip(pegawai.getNip());
		
		updatedPegawai.setNama(pegawai.getNama());
		updatedPegawai.setTempat_lahir(pegawai.getTempat_lahir());
		updatedPegawai.setTanggal_lahir(pegawai.getTanggal_lahir());
		updatedPegawai.setTahun_masuk(pegawai.getTahun_masuk());
		

		
	}



	



	@Override
	public List<PegawaiModel> getPegawaiDetailByIdInstansi(String instansiId) {
		// TODO Auto-generated method stub
		Long id=Long.parseLong(instansiId);
		return pegawaiDb.findByInstansiId(id);
	}



	@Override
	public PegawaiModel cariPegawaiTermuda(List<PegawaiModel> listPegawaiInstansi) {
		// TODO Auto-generated method stub
		PegawaiModel termuda=listPegawaiInstansi.get(0);
		
		for(PegawaiModel pegawai:listPegawaiInstansi) {
			int result=termuda.getTanggal_lahir().compareTo(pegawai.getTanggal_lahir());
			if(result<0) {
				termuda=pegawai;
			}
		}
		
		return termuda;
		
		
	}



	@Override
	public PegawaiModel cariPegawaiTertua(List<PegawaiModel> listPegawaiInstansi) {
		// TODO Auto-generated method stub
		PegawaiModel tertua=listPegawaiInstansi.get(0);
		
		for(PegawaiModel pegawai:listPegawaiInstansi) {
			int result=tertua.getTanggal_lahir().compareTo(pegawai.getTanggal_lahir());
			if(result>0) {
				tertua=pegawai;
			}
		}
		
		return tertua;
	}
	
	



	


	
	
	
	
	

}
