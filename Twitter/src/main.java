import java.io.FileNotFoundException;

import Trata.Shuffle;
import Trata.Vocabulario;

import NaiveBayes.Esqueleto;
import NaiveBayes.Corpo;
import NaiveBayes.Palavras;

import java.io.IOException;
import java.util.LinkedList;

public class main {
	public static void main(String[] args) throws FileNotFoundException {
		
		Shuffle sf = new Shuffle();
		sf.Embaralha();
		sf.CriaTreinamento();
		try {
			sf.CriaFolds();
			sf.CriaTreinoFolds();
		}
		catch(IOException e ) {
		}
		LinkedList <String> guarda = new LinkedList<String>();
		Vocabulario voca = new Vocabulario();
		
		
		guarda = voca.TiraRepetido("treinamento.txt");
		
		try {
			voca.escreveVocab(guarda);
		}
		catch(IOException e ) {
		}

		guarda = voca.RemoveNeutro();
		try {
			voca.escreveVocab(guarda);
		}
		catch(IOException e ) {
		}
		
		Esqueleto esq = new Corpo();
		//holdout simples
		esq.Distribui("treinamento.txt");
		esq.Bayes("Teste.txt","treinamento.txt");
		//crossvalidation
		
		double erroMedio = 0;
		
		for(int i = 1; i < 11; i++) {
			LinkedList <String> crossGuarda = new LinkedList<String>();
			Vocabulario crossVoca = new Vocabulario();
			crossGuarda = crossVoca.TiraRepetido("Treinofold"+Integer.toString(i)+".csv");
			
			try {
				crossVoca.escreveVocab(crossGuarda);
			}
			catch(IOException e ) {
			}

			crossGuarda = crossVoca.RemoveNeutro();
			try {
				crossVoca.escreveVocab(crossGuarda);
			}
			catch(IOException e ) {
			}
			esq.Distribui("Treinofold"+Integer.toString(i)+".csv");
			erroMedio += esq.Bayes("fold"+Integer.toString(i)+".csv","Treinofold"+Integer.toString(i)+".csv");
		}
		esq.escreveSaida(erroMedio/10,"Sentiment Analysis Dataset.csv");
		
		//holdout stopwords
		
		LinkedList <String> guardaStop = new LinkedList<String>();
		Vocabulario vocaStop = new Vocabulario();
		
		guardaStop = vocaStop.TiraRepetidoStop("treinamento.txt");
		
		try {
			vocaStop.escreveVocab(guardaStop);
		}
		catch(IOException e ) {
		}

		guardaStop = vocaStop.RemoveNeutro();
		try {
			vocaStop.escreveVocab(guardaStop);
		}
		catch(IOException e ) {
		}
		esq.Distribui("treinamento.txt");
		esq.Bayes("Teste.txt","treinamento.txt");
	}
}
