public class Algoritmo {

    public static final int GENERATIONS = 1000;
    public static final double PROB_MUTATION = 0.05;
    public static final double PROB_BEST = 0.2;
    public static final int POPULATION = 100;
    public static final int BOARD = 8;


    public int[][] generation = new int[POPULATION][BOARD];

    public int[][] newGeneration = new int[POPULATION][BOARD];

    public int numeberOfCrossovers = 0;




    private void createPopulation(){
        int max = 7;
        for(int i = 0; i < generation.length; i++){
            for(int j = 0; j < generation[i].length; j++){
                int rand = (int)(Math.random() * (max+1));
                generation[i][j] = rand;
            }
        }
    }

    private int calculateFF(int[] individual){
        int ff = 28;
        for(int i = 0; i < BOARD; i++){
            for(int j = i+1; j < BOARD; j++){
                if(individual[i] == individual[j])
                    ff--;
                else if(Math.abs(individual[i] - individual[j]) == Math.abs(i-j))
                    ff--;
            }
        }
        return ff;
    }

    public int[] selectFittestIndividuals(){
        int[] ffValues = new int[POPULATION];
        for(int i = 0; i < generation.length; i++)
          ffValues[i] =  calculateFF(generation[i]);

        int max = 99;
        int posRand1 = (int)(Math.random() * (max+1));
        int posRand2 = (int)(Math.random() * (max+1));
        int[] bestIndividual = generation[posRand1];
        if(ffValues[posRand1] < ffValues[posRand2])
                bestIndividual = generation[posRand2];
        return bestIndividual;
    }

    private int[] crossorverTwoIndividuals(int[] parent1, int[] parent2){
        int[] child = new int[8];
        int min = 1;
        int max = 7;
        int rand = (int)(Math.random() * (max - min +1 ) + min);
        for(int i = 0; i < rand ; i++){
            child[i] = parent1[i];
        }
        for(int i = rand; i < 8; i++){
            child[i] = parent2[i];
        }
       return child;
    }

    public void mutation(int[] array){
        double prob = Math.random();
        if(prob <= PROB_MUTATION){
            int randPosition = (int)(Math.random() * (7+1));
            int randNumber = (int)(Math.random() * (7+1));
            array[randPosition] = randNumber;
        }
    }

    public void writePopulation(int[] array){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Algoritmo alg = new Algoritmo();
        alg.createPopulation();
        for(int i = 0; i <= GENERATIONS; i++){

            int bestFitness = 0;
            int[] bestGame;
            for(int j = 0; j < Algoritmo.POPULATION; j++){
                int temp = alg.calculateFF(alg.generation[j]);
                if(temp > bestFitness)
                    bestFitness = temp;
                if(bestFitness == 28) {
                    System.out.println("Encontrei uma Solução:");
                    alg.writePopulation(alg.generation[j]);
                    return;
                }
            }
            System.out.println("Geração " + i + ": Melhor fitness = " + bestFitness);


            for(int j = 0; j < (int) (Algoritmo.POPULATION * Algoritmo.PROB_BEST); j++)
                alg.newGeneration[j] = alg.selectFittestIndividuals();




            for(int j = (int) (Algoritmo.POPULATION * Algoritmo.PROB_BEST); j < Algoritmo.POPULATION; j++){
                int randNumber1 = (int)(Math.random() * (Algoritmo.POPULATION * Algoritmo.PROB_BEST + 1));
                int randNumber2 = (int)(Math.random() * (Algoritmo.POPULATION * Algoritmo.PROB_BEST + 1));
                int[] temp = alg.crossorverTwoIndividuals(alg.newGeneration[randNumber1], alg.newGeneration[randNumber2]);
                alg.mutation(temp);
                alg.newGeneration[j] = temp;
            }
            alg.generation = alg.newGeneration;
        }
        System.out.println("NAO ENCONTREI NENHUMA SOLUÇÃO!!!!");
    }


}