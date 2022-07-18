package cn.raths.test;


import cn.raths.PetHomeApp;
import cn.raths.base.BaseTest;
import cn.raths.basic.utils.StrUtils;
import cn.raths.pet.domain.Pet;
import cn.raths.pet.domain.PetDetail;
import cn.raths.pet.service.IPetDetailService;
import cn.raths.pet.service.IPetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.lang.model.element.VariableElement;
import java.math.BigDecimal;
import java.util.Random;


public class PetTest extends BaseTest {

  @Autowired
  private IPetService petService;

  @Autowired
  private IPetDetailService petDetailService;

  @Test
  public void initDataTest() throws Exception{

    // 以数据库中宠物/宠物详情第一条数据为例，进行数据伪造
    Long petId = 1L;
    Pet pet = petService.loadById(petId);
    PetDetail petDetail = petDetailService.loadByPetId(pet.getId());

    pet.setId(null);
    petDetail.setId(null);

    for (int i = 0; i < 100; i++) {
      pet.setState(i % 2);

      if (i%3==0){
        pet.setName(StrUtils.getRandomHanZiNoSpace(2));
        pet.setResources("/group1/M00/00/8F/CgAIC2LTmJWANTvIAAGiy4cRykU95.jpeg,/group1/M00/00/8F/CgAIC2LTmJuAKaGdAAFiSPSAbLY27.jpeg,/group1/M00/00/8F/CgAIC2LTmKGAQXySAAFCqcHQlDM75.jpeg");
      }
      else if(i%3==1){
        pet.setName(StrUtils.getRandomHanZiNoSpace(3));
        pet.setResources("/group1/M00/00/8F/CgAIC2LTmJuAKaGdAAFiSPSAbLY27.jpeg,/group1/M00/00/8F/CgAIC2LTmJWANTvIAAGiy4cRykU95.jpeg,/group1/M00/00/8F/CgAIC2LTmKGAQXySAAFCqcHQlDM75.jpeg");

      }
      else{
        pet.setName(StrUtils.getRandomHanZiNoSpace(4));

        pet.setResources("/group1/M00/00/8F/CgAIC2LTmKGAQXySAAFCqcHQlDM75.jpeg,/group1/M00/00/8F/CgAIC2LTmJWANTvIAAGiy4cRykU95.jpeg,/group1/M00/00/8F/CgAIC2LTmJuAKaGdAAFiSPSAbLY27.jpeg");
      }
      Random rd = new Random();
      int a = rd.nextInt(900) + 100;
      pet.setCostprice(new BigDecimal(a / 2));
      pet.setSaleprice(new BigDecimal(a));
      petService.save(pet);
      petDetail.setPetId(pet.getId());
      petDetailService.save(petDetail);
    }
  }
}