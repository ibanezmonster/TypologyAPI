package com.typology.service.typingCRUD;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.typology.entity.entry.Entry;
import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.user.Typist;
import com.typology.repository.EnneagramTypingRepository;
import com.typology.repository.EntryRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class EnneagramTypingCRUD
{
	@Autowired
	EnneagramTypingRepository enneagramTypingRepository;
	
	@Autowired
	EntryRepository entryRepository;

	//void deleteTyping() {}
//	private boolean isDeleted;					//implement soft delete to track whether something is deleted or not, there's a column for this also
//	
//	@PreRemove									//"hook" (jpa life cycle method) that fires when a row is deleted- used to set the data member isDeleted to true
//	private void preRemove(){
//		LOGGER.info("Setting isDeleted to True");
//		this.isDeleted = true;
//	}
	
	public EnneagramTypingCRUD(EnneagramTypingRepository enneagramTypingRepository) {
		this.enneagramTypingRepository = enneagramTypingRepository;
	}
	
	public EnneagramTypingCRUD(EnneagramTypingRepository enneagramTypingRepository, EntryRepository entryRepository) {
		this.enneagramTypingRepository = enneagramTypingRepository;
		this.entryRepository = entryRepository;
	}
	
	
	public void createEnneagramTyping(String entryName, Typist typist, EnneagramTyping enneagramTyping) throws DataIntegrityViolationException{
		
		//handle scenario of record already exists
		if(enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entryName)
								 	.isPresent()) 
		{
			throw new DataIntegrityViolationException("Typing already exists.");
		}
		
		//calculate fields to insert into database
		enneagramTyping.setInstinctStackFlow(calculateInstinctStackFlow(enneagramTyping.getInstinctStack()));
		enneagramTyping.setExInstinctStackAbbreviation(calculateExInstinctStackAbbreviation(enneagramTyping.getExInstinctStack()));
		enneagramTyping.setExInstinctStackFlow(calculateExInstinctStackFlow(enneagramTyping.getExInstinctStack()));
		enneagramTyping.setTritypeUnordered(calculateTritypeUnordered(enneagramTyping.getTritypeOrdered()));
		
		
		//get entryId for fk
		Entry entry = this.entryRepository.findByName(entryName)
										  .orElseThrow(ResourceNotFoundException::new);			
		enneagramTyping.setEntry(entry);
		enneagramTyping.setTypist(typist);
				
		//add enneagram typing table record
		enneagramTypingRepository.save(enneagramTyping);
	}
	
	
	
	
	public Optional<EnneagramTyping> readEnneagramTyping(String typistName, String entryName) throws DataIntegrityViolationException{
		return enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typistName, entryName);
	}	
	
	
	
	
	
	public void updateEnneagramTyping(String entryName, Typist typist, EnneagramTyping enneagramTyping) throws Exception{

		//get existing enneagram typing in the db for fk
		EnneagramTyping enneagramTypingToUpdate = enneagramTypingRepository.findEnneagramTypingByTypistAndEntryName(typist.getName(), entryName)
												  						   .orElseThrow(ResourceNotFoundException::new);	

		enneagramTypingToUpdate.setId(enneagramTypingToUpdate.getId());
		
		enneagramTypingToUpdate.setCoreType(enneagramTyping.getCoreType());
		enneagramTypingToUpdate.setWing(enneagramTyping.getWing());
		enneagramTypingToUpdate.setTritypeOrdered(enneagramTyping.getTritypeOrdered());
		enneagramTypingToUpdate.setOverlay(enneagramTyping.getOverlay());
		enneagramTypingToUpdate.setInstinctMain(enneagramTyping.getInstinctMain());
		enneagramTypingToUpdate.setInstinctStack(enneagramTyping.getInstinctStack());
		enneagramTypingToUpdate.setExInstinctMain(enneagramTyping.getExInstinctMain());
		enneagramTypingToUpdate.setExInstinctStack(enneagramTyping.getExInstinctStack());
		
		//calculate fields to insert into database
		enneagramTypingToUpdate.setInstinctStackFlow(calculateInstinctStackFlow(enneagramTyping.getInstinctStack()));
		enneagramTypingToUpdate.setExInstinctStackAbbreviation(calculateExInstinctStackAbbreviation(enneagramTyping.getExInstinctStack()));
		enneagramTypingToUpdate.setExInstinctStackFlow(calculateExInstinctStackFlow(enneagramTyping.getExInstinctStack()));
		enneagramTypingToUpdate.setTritypeUnordered(calculateTritypeUnordered(enneagramTyping.getTritypeOrdered()));

		
		enneagramTypingRepository.save(enneagramTypingToUpdate);	
	}


	
	
	public void deleteEnneagramTyping(EnneagramTyping enneagramTypingToDelete) throws Exception{

		enneagramTypingRepository.deleteById(enneagramTypingToDelete.getId());	
	}	
	
	
	
	
	
	private String calculateInstinctStackFlow(String instinctStack){
		String instinctStackFlow = "";
		
		//handle null
		if(instinctStack == null) {
			return "xxxx";
		}
		
		instinctStackFlow = switch(instinctStack) {
								case "sp/sx", "sx/so", "so/sp" : yield "contraflow";
								case "sp/so", "so/sx", "sx/sp" : yield "synflow";
								default : yield "xxxx";
							};
		
		
		return instinctStackFlow;
	}

	
	
	
	
	private int calculateExInstinctStackAbbreviation(String exInstinctStack){
		int exInstinctStackAbbreviation = 000;
		
		//handle null
		if(exInstinctStack == null) {
			return 000;
		}
		
		exInstinctStackAbbreviation = switch(exInstinctStack) {
						
									   case "FD/AY/SS" : yield 825; 
									   case "FD/AY/EX" : yield 826; 
									   case "FD/AY/UN" : yield 827;
									   case "FD/CY/SS" : yield 835;
									   case "FD/CY/EX" : yield 836;
									   case "FD/CY/UN" : yield 837;
									   case "FD/BG/SS" : yield 845;
									   case "FD/BG/EX" : yield 846;
									   case "FD/BG/UN" : yield 847;
											
									   case "SY/AY/SS" : yield 925;
									   case "SY/AY/EX" : yield 926;
									   case "SY/AY/UN" : yield 927;
									   case "SY/CY/SS" : yield 935;
									   case "SY/CY/EX" : yield 936;
									   case "SY/CY/UN" : yield 937;
									   case "SY/BG/SS" : yield 945;
									   case "SY/BG/EX" : yield 946;
									   case "SY/BG/UN" : yield 947;
											
									   case "SM/AY/SS" : yield 125;
									   case "SM/AY/EX" : yield 126;
									   case "SM/AY/UN" : yield 127;
									   case "SM/CY/SS" : yield 135;
									   case "SM/CY/EX" : yield 136;
									   case "SM/CY/UN" : yield 137;
									   case "SM/BG/SS" : yield 145;
									   case "SM/BG/EX" : yield 146;
									   case "SM/BG/UN" : yield 147;
											
									   case "FD/SS/AY" : yield 852;
									   case "FD/SS/CY" : yield 853;
									   case "FD/SS/BG" : yield 854;
									   case "FD/EX/AY" : yield 862;
									   case "FD/EX/CY" : yield 863;
									   case "FD/EX/BG" : yield 864;
									   case "FD/UN/AY" : yield 872;
									   case "FD/UN/CY" : yield 873;
									   case "FD/UN/BG" : yield 874;
											
									   case "SY/SS/AY" : yield 952;
									   case "SY/SS/CY" : yield 953;
									   case "SY/SS/BG" : yield 954;
									   case "SY/EX/AY" : yield 962;
									   case "SY/EX/CY" : yield 963;
									   case "SY/EX/BG" : yield 964;
									   case "SY/UN/AY" : yield 972;
									   case "SY/UN/CY" : yield 973;
									   case "SY/UN/BG" : yield 974;
											
									   case "SM/SS/AY" : yield 152;
									   case "SM/SS/CY" : yield 153;
									   case "SM/SS/BG" : yield 154;
									   case "SM/EX/AY" : yield 162;
									   case "SM/EX/CY" : yield 163;
									   case "SM/EX/BG" : yield 164;
									   case "SM/UN/AY" : yield 172;
									   case "SM/UN/CY" : yield 173;
									   case "SM/UN/BG" : yield 174;
											
									   case "AY/FD/SS" : yield 285;
									   case "AY/FD/EX" : yield 286;
									   case "AY/FD/UN" : yield 287;
									   case "AY/SY/SS" : yield 295;
									   case "AY/SY/EX" : yield 296;
									   case "AY/SY/UN" : yield 297;
									   case "AY/SM/SS" : yield 215;
									   case "AY/SM/EX" : yield 216;
									   case "AY/SM/UN" : yield 217;
											
									   case "CY/FD/SS" : yield 385;
									   case "CY/FD/EX" : yield 386;
									   case "CY/FD/UN" : yield 387;
									   case "CY/SY/SS" : yield 395;
									   case "CY/SY/EX" : yield 396;
									   case "CY/SY/UN" : yield 397;
									   case "CY/SM/SS" : yield 315;
									   case "CY/SM/EX" : yield 316;
									   case "CY/SM/UN" : yield 317;
											
									   case "BG/FD/SS" : yield 485;
									   case "BG/FD/EX" : yield 486;
									   case "BG/FD/UN" : yield 487;
									   case "BG/SY/SS" : yield 495;
									   case "BG/SY/EX" : yield 496;
									   case "BG/SY/UN" : yield 497;
									   case "BG/SM/SS" : yield 415;
									   case "BG/SM/EX" : yield 416;
									   case "BG/SM/UN" : yield 417;
											
									   case "AY/SS/FD" : yield 258;
									   case "AY/SS/SY" : yield 259;
									   case "AY/SS/SM" : yield 251;
									   case "AY/EX/FD" : yield 268;
									   case "AY/EX/SY" : yield 269;
									   case "AY/EX/SM" : yield 261;
									   case "AY/UN/FD" : yield 278;
									   case "AY/UN/SY" : yield 279;
									   case "AY/UN/SM" : yield 271;
											
									   case "CY/SS/FD" : yield 358;
									   case "CY/SS/SY" : yield 359;
									   case "CY/SS/SM" : yield 351;
									   case "CY/EX/FD" : yield 368;
									   case "CY/EX/SY" : yield 369;
									   case "CY/EX/SM" : yield 361;
									   case "CY/UN/FD" : yield 378;
									   case "CY/UN/SY" : yield 379;
									   case "CY/UN/SM" : yield 371;
											
									   case "BG/SS/FD" : yield 458;
									   case "BG/SS/SY" : yield 459;
									   case "BG/SS/SM" : yield 451;
									   case "BG/EX/FD" : yield 468;
									   case "BG/EX/SY" : yield 469;
									   case "BG/EX/SM" : yield 461;
									   case "BG/UN/FD" : yield 478;
									   case "BG/UN/SY" : yield 479;
									   case "BG/UN/SM" : yield 471;
											
									   case "SS/FD/AY" : yield 582;
									   case "SS/FD/CY" : yield 583;
									   case "SS/FD/BG" : yield 584;
									   case "SS/EX/AY" : yield 592;
									   case "SS/EX/CY" : yield 593;
									   case "SS/EX/BG" : yield 594;
									   case "SS/SM/AY" : yield 512;
									   case "SS/SM/CY" : yield 513;
									   case "SS/SM/BG" : yield 514;
											
									   case "EX/FD/AY" : yield 682;
									   case "EX/FD/CY" : yield 683;
									   case "EX/FD/BG" : yield 684;
									   case "EX/EX/AY" : yield 692;
									   case "EX/EX/CY" : yield 693;
									   case "EX/EX/BG" : yield 694;
									   case "EX/SM/AY" : yield 612;
									   case "EX/SM/CY" : yield 613;
									   case "EX/SM/BG" : yield 614;
											
									   case "UN/FD/AY" : yield 782;
									   case "UN/FD/CY" : yield 783;
									   case "UN/FD/BG" : yield 784;
									   case "UN/EX/AY" : yield 792;
									   case "UN/EX/CY" : yield 793;
									   case "UN/EX/BG" : yield 794;
									   case "UN/SM/AY" : yield 712;
									   case "UN/SM/CY" : yield 713;
									   case "UN/SM/BG" : yield 714;
											
									   case "SS/AY/FD" : yield 528;
									   case "SS/AY/SY" : yield 529;
									   case "SS/AY/SM" : yield 521;
									   case "SS/CY/FD" : yield 538;
									   case "SS/CY/SY" : yield 539;
									   case "SS/CY/SM" : yield 531;
									   case "SS/BG/FD" : yield 548;
									   case "SS/BG/SY" : yield 549;
									   case "SS/BG/SM" : yield 541;
											
									   case "EX/AY/FD" : yield 628;
									   case "EX/AY/SY" : yield 629;
									   case "EX/AY/SM" : yield 621;
									   case "EX/CY/FD" : yield 638;
									   case "EX/CY/SY" : yield 639;
									   case "EX/CY/SM" : yield 631;
									   case "EX/BG/FD" : yield 648;
									   case "EX/BG/SY" : yield 649;
									   case "EX/BG/SM" : yield 641;
											
									   case "UN/AY/FD" : yield 728;
									   case "UN/AY/SY" : yield 729;
									   case "UN/AY/SM" : yield 721;
									   case "UN/CY/FD" : yield 738;
									   case "UN/CY/SY" : yield 739;
									   case "UN/CY/SM" : yield 731;
									   case "UN/BG/FD" : yield 748;
									   case "UN/BG/SY" : yield 749;
									   case "UN/BG/SM" : yield 741;
									   
									   default : yield 000;
										};
		
		return exInstinctStackAbbreviation;
	}

	
	
	
	
	private String calculateExInstinctStackFlow(String exInstinctStack){
		String exInstinctStackFlow = "xxx";
		
		//handle null
		if(exInstinctStack == null) {
			return "xxx";
		}
		
		exInstinctStackFlow = switch(exInstinctStack) {
								   case "FD/AY/SS", "FD/AY/EX", "FD/AY/UN", 
										"FD/CY/SS", "FD/CY/EX", "FD/CY/UN", 
										"FD/BG/SS", "FD/BG/EX", "FD/BG/UN",
										
										"SY/AY/SS", "SY/AY/EX", "SY/AY/UN", 
										"SY/CY/SS", "SY/CY/EX", "SY/CY/UN", 
										"SY/BG/SS", "SY/BG/EX", "SY/BG/UN",
										
										"SM/AY/SS", "SM/AY/EX", "SM/AY/UN", 
										"SM/CY/SS", "SM/CY/EX", "SM/CY/UN", 
										"SM/BG/SS", "SM/BG/EX", "SM/BG/UN"
											: yield "SIP";
										
								   case "FD/SS/AY", "FD/SS/CY", "FD/SS/BG", 
										"FD/EX/AY", "FD/EX/CY", "FD/EX/BG", 
										"FD/UN/AY", "FD/UN/CY", "FD/UN/BG",
										
										"SY/SS/AY", "SY/SS/CY", "SY/SS/BG", 
										"SY/EX/AY", "SY/EX/CY", "SY/EX/BG", 
										"SY/UN/AY", "SY/UN/CY", "SY/UN/BG",
										
										"SM/SS/AY", "SM/SS/CY", "SM/SS/BG", 
										"SM/EX/AY", "SM/EX/CY", "SM/EX/BG", 
										"SM/UN/AY", "SM/UN/CY", "SM/UN/BG"
											: yield "SPI";
										
								   case "AY/FD/SS", "AY/FD/EX", "AY/FD/UN", 
								   		"AY/SY/SS", "AY/SY/EX", "AY/SY/UN", 
								   		"AY/SM/SS", "AY/SM/EX", "AY/SM/UN",
										
								   		"CY/FD/SS", "CY/FD/EX", "CY/FD/UN", 
								   		"CY/SY/SS", "CY/SY/EX", "CY/SY/UN", 
								   		"CY/SM/SS", "CY/SM/EX", "CY/SM/UN",
										
								   		"BG/FD/SS", "BG/FD/EX", "BG/FD/UN", 
								   		"BG/SY/SS", "BG/SY/EX", "BG/SY/UN", 
								   		"BG/SM/SS", "BG/SM/EX", "BG/SM/UN"
											: yield "ISP";
										
								   case "AY/SS/FD", "AY/SS/SY", "AY/SS/SM", 
								   		"AY/EX/FD", "AY/EX/SY", "AY/EX/SM", 
								   		"AY/UN/FD", "AY/UN/SY", "AY/UN/SM",
										
								   		"CY/SS/FD", "CY/SS/SY", "CY/SS/SM", 
								   		"CY/EX/FD", "CY/EX/SY", "CY/EX/SM", 
								   		"CY/UN/FD", "CY/UN/SY", "CY/UN/SM",
										
								   		"BG/SS/FD", "BG/SS/SY", "BG/SS/SM", 
								   		"BG/EX/FD", "BG/EX/SY", "BG/EX/SM", 
								   		"BG/UN/FD", "BG/UN/SY", "BG/UN/SM"
											: yield "IPS";
										
								   case "SS/FD/AY", "SS/FD/CY", "SS/FD/BG", 
								   		"SS/EX/AY", "SS/EX/CY", "SS/EX/BG", 
								   		"SS/SM/AY", "SS/SM/CY", "SS/SM/BG",
										
								   		"EX/FD/AY", "EX/FD/CY", "EX/FD/BG", 
								   		"EX/EX/AY", "EX/EX/CY", "EX/EX/BG", 
								   		"EX/SM/AY", "EX/SM/CY", "EX/SM/BG",
										
								   		"UN/FD/AY", "UN/FD/CY", "UN/FD/BG", 
								   		"UN/EX/AY", "UN/EX/CY", "UN/EX/BG", 
								   		"UN/SM/AY", "UN/SM/CY", "UN/SM/BG"
											: yield "PSI";
										
								   case "SS/AY/FD", "SS/AY/SY", "SS/AY/SM", 
								   		"SS/CY/FD", "SS/CY/SY", "SS/CY/SM", 
								   		"SS/BG/FD", "SS/BG/SY", "SS/BG/SM",
										
								   		"EX/AY/FD", "EX/AY/SY", "EX/AY/SM", 
								   		"EX/CY/FD", "EX/CY/SY", "EX/CY/SM", 
								   		"EX/BG/FD", "EX/BG/SY", "EX/BG/SM",
										
								   		"UN/AY/FD", "UN/AY/SY", "UN/AY/SM", 
								   		"UN/CY/FD", "UN/CY/SY", "UN/CY/SM", 
								   		"UN/BG/FD", "UN/BG/SY", "UN/BG/SM"
											: yield "PIS";
										
									default : yield "xxx";
									};
		
		return exInstinctStackFlow;
	}
	
	
	
	
	
	
	private int calculateTritypeUnordered(int tritypeOrdered){
		int tritypeUnordered = 000;
		
		//handle null
		if(tritypeOrdered == 0) {
			return 0;
		}
		
		tritypeUnordered = switch(tritypeOrdered) {

							case 125, 152, 215, 251, 512, 521 : yield 125;
							case 126, 162, 216, 261, 612, 621 : yield 126;
							case 127, 172, 217, 271, 712, 721 : yield 127;
							case 135, 153, 315, 351, 513, 531 : yield 135;
							case 136, 163, 316, 361, 613, 631 : yield 136;
							case 137, 173, 317, 371, 713, 731 : yield 137;
							case 145, 154, 415, 451, 514, 541 : yield 145;
							case 146, 164, 416, 461, 614, 641 : yield 146;
							case 147, 174, 417, 471, 714, 741 : yield 147;
							
							case 258, 285, 528, 582, 825, 852 : yield 258;
							case 259, 295, 529, 592, 925, 952 : yield 259;
							case 268, 286, 628, 682, 826, 862 : yield 268;
							case 269, 296, 629, 692, 926, 962 : yield 269;
							case 278, 287, 728, 782, 827, 872 : yield 278;
							case 279, 297, 729, 792, 927, 972 : yield 279;
							
							
							case 358, 385, 538, 583, 835, 853 : yield 358;
							case 359, 395, 539, 593, 935, 953 : yield 359;
							case 368, 386, 638, 683, 836, 863 : yield 368;
							case 378, 387, 738, 783, 837, 873 : yield 378;
							case 369, 396, 639, 693, 936, 963 : yield 369;
							case 379, 397, 739, 793, 937, 973 : yield 379;
							
							case 458, 485, 548, 584, 845, 854 : yield 458;
							case 468, 486, 648, 684, 846, 864 : yield 468;
							case 469, 496, 649, 694, 946, 964 : yield 469;
							case 478, 487, 748, 784, 847, 874 : yield 478;
							case 479, 497, 749, 794, 947, 974 : yield 479;
							
							default : yield 000;
						   };
		
		
		return tritypeUnordered;
	}	
}
