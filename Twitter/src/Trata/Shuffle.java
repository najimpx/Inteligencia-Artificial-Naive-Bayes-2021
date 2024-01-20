package Trata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

public class Shuffle {

	public void  Embaralha () {
		List<String> Linhas = new ArrayList<String>();
		
		
		try (FileReader reader = new FileReader("Sentiment Analysis Dataset.csv");
	    BufferedReader br = new BufferedReader(reader)) {
			

			String line;
	    	int firstLine = 0;
			
			while ((line = br.readLine()) != null) {
	      	   if(firstLine == 0) {
	     		   firstLine++;
	     		   continue;
	     	   }
	     	  Linhas.add(line + "\n");
			}
			reader.close();
		} catch (IOException e ) {System.err.format("IOException: %s%n", e);};
			
				
		Collections.shuffle(Linhas);
	
	try {
		FileWriter writer = new FileWriter("Sentiment Analysis Dataset.csv");
		for(String str: Linhas) {
			  writer.write(str);
			}
			writer.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

    public void CriaFolds() throws IOException 
    {
        List<String> Linhas = new ArrayList<String>();
        try (FileReader reader = new FileReader("Sentiment Analysis Dataset.csv");
        BufferedReader br = new BufferedReader(reader)) {	
        	String line;
                    
        	while ((line = br.readLine()) != null) {
        		Linhas.add(line + "\n");
        	}
                     
                    
        	reader.close();
        } 
        catch (IOException e ) {
        	System.err.format("IOException: %s%n", e);
        }
        //FOLDS EM CSV
        
        int controladora = 1;
        int texto = 2;
        try {
			FileWriter writer = new FileWriter("fold1.csv");
			int TamanhoLista = Linhas.size();
			for(int i = 0; i < TamanhoLista/10; i++) {
				writer.append(Linhas.get(i));
			}
	        writer.flush();
			writer.close();           
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(int i = controladora; i < 10;controladora++)
        {
            try {
            	FileWriter writer = new FileWriter("fold"+texto+".csv");
                for(int x = (Linhas.size()/10)*i; x < (Linhas.size()/10)*(i+1); x++) {
                	if(x >=Linhas.size()){
	                    break;
	                };
	                writer.append(Linhas.get(x));
                }
	            writer.flush();
	            writer.close();
	            i++;
	            texto++;
                        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

	public void CriaTreinamento () {
		List<String> Linhas = new ArrayList<String>();
	
	
		try (FileReader reader = new FileReader("Sentiment Analysis Dataset.csv");
			BufferedReader br = new BufferedReader(reader)) {
			
			
			String line;
			int firstLine = 0;
		
			while ((line = br.readLine()) != null) {
				if(firstLine == 0) {
					firstLine++;
					continue;
				}
			Linhas.add(line + "\n");
			}
			reader.close();
		} catch (IOException e ) {System.err.format("IOException: %s%n", e);};
	

		try {
			FileWriter writer = new FileWriter("Teste.txt");
			int TamanhoLista = Linhas.size();
			for(int i = (TamanhoLista/3)*2; i < TamanhoLista; i++) {
				  writer.write(Linhas.get(i));
				}
				writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter writer = new FileWriter("Treinamento.txt");
			int TamanhoLista = Linhas.size();
			for(int i = 0; i < (TamanhoLista/3)*2; i++) {
				  writer.write(Linhas.get(i));
				}
				writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void CriaTreinoFolds() throws IOException 
    {
        for(int i = 1; i<11; i++) {
            List<String> Linhas = new ArrayList<String>();
	        for(int j = 1; j < 11; j++) {
	        	if(i == j) {
	        		continue;
	        	}
		        try (FileReader reader = new FileReader("fold"+Integer.toString(j)+".csv");
		        BufferedReader br = new BufferedReader(reader)) {	
		        	String line = null;
		        	while ((line = br.readLine()) != null) {
		        		Linhas.add(line + "\n");
		        	}
		        	reader.close();
		        } 
		        catch (IOException e ) {
		        	System.err.format("IOException: %s%n", e);
		        }
	        }
	        //FOLDS EM CSV
			FileWriter writer = new FileWriter("Treinofold"+Integer.toString(i)+".csv");
			int TamanhoLista = Linhas.size();
			for(int z = 0; z < TamanhoLista; z++) {
				writer.append(Linhas.get(z));
			}
		    writer.flush();
			writer.close();
        }
    }
}
