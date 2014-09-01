/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author haye
 */
public class Main {
    static ObjectMapper mapper = new ObjectMapper();
  
    public static void main(String [] args) throws IOException, JSONException {

        //Test verileri 
        
        Model modeller [] = new Model[2];
        
        for(int i=0;i<2;i++){
            Model m = new Model();
            m.setAd("Hasan" + i);
            m.setSoyad("Yetiş");
            modeller[i] = m;
        }
     
        //TEST
        System.out.println(Object2String(modeller));   
        System.out.println(Object2String(String2Object("{\"totalCount\":2,\"data\":\"[{\\\"ad\\\":\\\"Hasan3\\\",\\\"soyad\\\":\\\"Yetiş\\\"},{\\\"ad\\\":\\\"Hasan4\\\",\\\"soyad\\\":\\\"Yetiş\\\"},{\\\"ad\\\":\\\"Hasan7\\\",\\\"soyad\\\":\\\"Yetiş\\\"}]\",\"success\":true}")));
    }
    
    
    
     public static String Object2String(Model [] object) throws IOException {
        
        //gelen diziyi, ilgili siniftaki değişken isimleri key olarak json dizisine çeviriyor
        String data = mapper.writeValueAsString(object);
                   
        //Oluşturulan diziye ek bilgiler de eklenerek gönderiliyor
        try {
            JSONObject q = new JSONObject();

            q.put("data", data);
            q.put("success",true);
            q.put("totalCount",  object.length);
            
            return q.toString();
        }catch (JSONException ex) {
            return "{\"success\" : false, \"message\" : \""+ ex.getMessage() + "\"}";
        }
        
    }
    
    
    public static Model [] String2Object(String json) throws JSONException, IOException {
        //Gelen string JSon objesine çevriliyor
        JSONObject q = new JSONObject(json);
        
        //Dİğer bilgileri elenip sadece veri kismi aliniyor 
        String x = q.get("data").toString();
        
        //Veri sayisi boyutunda dizi oluşturuluyor
        int sayi = Integer.parseInt(q.get("totalCount").toString());
        Model m[] = new Model[sayi];
        
        //Oluşturulan diziye çevrilen nesneler ataniyor
        m = mapper.readValue(x, Model[].class);
        
        return m;
    }
    
    /*
        Sinif isminde <Model extends Object> diyerek Model sinifi da dinamikleştirirsek bu işimizi görür diye düşünüyorum
    */
}
