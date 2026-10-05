package SlotMachine_Package;
import java.util.ArrayList;
import java.util.Random;

/**
    * Imperial Chance & Play Casino offers games using a slot machine that has n wheels arranged next to
    * each other. Each of the wheels has n distinct symbols on it, and these symbols appear in the same order
    * on each wheel. Each wheel shows one of its symbols through a window on the front of the machine,
    * which results in a sequence of n symbols being shown next to each other.
    * You are standing behind the machine and notice that a maintenance panel has been left open. When
    * you stick your hand inside, you are able to secretly rotate any of the wheels by any number of steps,
    * thus changing the symbol shown on that wheel. You want to win a jackpot, which will happen if all the
    * wheels show the same symbol at the same time. Unfortunately, you cannot see the symbols from your
    * position, so you asked your good friend to help you. The friend is standing in front of the machine and
    * she tells you the number of distinct symbols in the sequence she can currently see. Can you win the
    * jackpot by manipulating the wheels if your friend updates the information after every action you make?
    * The first line of input contains an integer n (3 ≤ n ≤ 50), giving the number of wheels and symbols in
    * the machine.
    * Interaction then proceeds in rounds. In each round, one line of input becomes available, containing an
    * integer k (1 ≤ k ≤ n), the number of distinct symbols in the current sequence. If k > 1, output two
    * integers i and j (1 ≤ i ≤ n; −10^9 ≤ j ≤ 10x^9), representing your action: rotating the ith wheel by
    * j positions, where negative numbers indicate rotating in the opposite direction. Otherwise, if k = 1,
    * indicating that all wheels show the same symbol, your program must exit without printing more output.
    * At most 10 000 actions are allowed – if your submission uses more rounds, it will not be accepted. It
    * is guaranteed that the initial configuration of wheels does not already have all wheels showing the same
    * symbol (k > 1 in the first round).
    * The judge program will not behave in an adversarial way, which means the initial configuration is fixed
    * before the first action.
    * A testing tool is provided to help you develop and test your solution.
*/


public abstract class SlotMachineContest
{
    /**
     * Resuelve el problema de la maraton
     * @param n cantidad de ruedas y simbolos 3 <= n <= 50
     * @return un arreglo que contiene los pasos que debe realizar SlotMachine para quedar en estado de Jackpot
     */
    public static int[][] solve(int n) 
    {
        // Validacion inicial del problema
        
        if(n < 3 || n > 50) 
        {
            return new int[0][0];
        }
        
        // Se crea un arreglo que guardara los indices a los que apunta cada rueda de forma aleatoria
        
        int[] indexes = new int[n];
        Random rand = new Random();
        
        for(int i = 0; i < n; i++) 
        {
            indexes[i] = rand.nextInt(n);
        }

        // Se crea un arreglo que guarda la lista de movimientos y se guarda en k la cantidad de simbolos distintos
        
        ArrayList<int[]> movesList = new ArrayList<int[]>();
        int k = distinctSymbols(indexes, n);

        if(k == 1) 
        {
            return movesList.toArray(new int[0][0]);
        }

        for(int i = 1; i <= n && k < n; i++) 
        {
            int startK = k;
            boolean progress = false;

            for(int j = 1; j < n; j++) 
            {
                k = spin(i, 1, n, indexes, movesList);
                
                if(k == 1) 
                {
                    return movesList.toArray(new int[0][0]);
                }

                if(k > startK) 
                {
                    progress = true;
                    break;
                }
            }

            if(!progress) 
            {
                k = spin(i, 1, n, indexes, movesList);
            }
        }

        int[] offset = new int[n + 1];

        for(int dist = 1; dist < n; dist++) 
        {
            k = spin(1, 1, n, indexes, movesList);

            for(int w = 2; w <= n; w++) 
            {
                if(offset[w] != 0) 
                {
                    continue;
                }

                k = spin(w, -dist, n, indexes, movesList);

                if(k == n) 
                {
                    offset[w] = dist;
                    k = spin(w, dist, n, indexes, movesList);
                    break;
                } 
                else 
                {
                    k = spin(w, dist, n, indexes, movesList);
                }
            }
        }

        spin(1, 1, n, indexes, movesList);

        for(int w = 2; w <= n; w++) 
        {
            k = spin(w, -offset[w], n, indexes, movesList);
            
            if(k == 1) 
            {
                return movesList.toArray(new int[0][0]);
            }
        }
        
        return movesList.toArray(new int[0][0]);
    }

    /**
     * Calcula la cantidad de simbolos distintos que hay en SlotMachine
     * @param indexes el arreglo que contiene a donde apunta cada rueda
     * @param n cantidad de ruedas y simbolos
     */
    private static int distinctSymbols(int[] indexes, int n) 
    {
        ArrayList<Integer> distinct = new ArrayList<Integer>();
        
        for(int i = 0; i < n; i++)
        {
            if(distinct.contains(indexes[i]) == false)
            {
                distinct.add(indexes[i]);
            }
        }
        
        return distinct.size();
    }
    
    /**
     * Gira una rueda cierta cantidad de pasos
     * @param wheel posicion de la rueda a girar
     * @param steps cantidad de pasos que se movera la rueda
     * @param n cantidad de ruedas y simbolos
     * @param indexes el arreglo que contiene a donde apunta cada rueda
     * @param movesList movimientos que se han guardado hasta el momento en donde se llama spin, esta lista de movimientos se actualiza durante la ejecucion del metodo
     * @return cantidad de simbolos distintos en todas las ruedas
     */
    private static int spin(int wheel, int steps, int n, int[] indexes, ArrayList<int[]> movesList) 
    {
        int index = wheel - 1; 
    
        if(steps > 0)
        {
            for(int i = 0; i < steps; i++)
            {
                indexes[index] = (indexes[index] + 1) % n;
            }
        }
        else
        {
            for(int i = 0; i < -steps; i++)
            {    
                indexes[index]--;
                
                if(indexes[index] < 0)
                {
                    indexes[index] = n - 1;
                }
            }
        }
        
        movesList.add(new int[]{wheel, steps});
        return distinctSymbols(indexes, n);
    }

    /**
     * Simulacion del funcionamiento del algoritmo de SlotMachine
     * @param n cantidad de ruedas y simbolos 3 <= n <= 50
     */
    public static void simulate(int n)
    {
        //Validacion inicial del problema
        
        if(n < 3 || n > 50)
        {
            return;
        }

        SlotMachine sm = new SlotMachine(n);
        sm.makeVisible();
        int k = sm.distinctSymbols();
        
        if(k == 1)
        {    
            return;
        }
        
        //Primero se quiere hacer que en la maquina cada rueda quede con un simbolo distinto 
        //para posteriormente poder calcular las distancias de los otros simbolos con respecto al
        //simbolo de la primera rueda
        
        for(int i = 1; i <= n && k < n; i++) 
        {
            int startK = sm.distinctSymbols();
            boolean progress = false;
            
            // Se va a girar la rueda por cada uno de los simbolos hasta que la cantidad de simbolos distintos aumente
            
            for(int j = 1; j < n; j++) 
            {
                sm.spin(i, 1);
                k = sm.distinctSymbols();
                
                if(k == 1)
                {
                    return;
                }

                if(k > startK) 
                {
                    progress = true;
                    break;
                }
            }
            
            // Si la cantidad de simbolos distintos no aumenta al hacer que la rueda recorra todos los simbolos existentes
            // se hara que la rueda gire de nuevo para que vuelva a la posicion donde se encontraba antes y de esta manera
            // se garantize que al finalizar de recorrer todas las ruedas los simbolos sean distintos en cada una de ellas
            
            if(!progress) 
            {
                sm.spin(i, 1);
                k = sm.distinctSymbols();
            }
        }
        
        // Se crea un arreglo que en cada rueda guarda la distancia del simbolo que contiene con respecto al simbolo de la primera rueda
        // Se crea con un tamaño n + 1 para que la rueda corresponda directamente con la posicion del arreglo
        
        int[] offset = new int[n + 1];
        
        // Se cambia el simbolo de la primera rueda al girarla, lo que genera que la primera rueda pase a tener el simbolo que correspondia originalmente a otra rueda
        
        for(int dist = 1; dist < n; dist++) 
        {
            sm.spin(1, 1);
            k = sm.distinctSymbols();
            
            // Se busca la rueda en la cual coincidio el simbolo, que puede ser desde la segunda rueda en adelante
            
            for(int w = 2; w <= n; w++) 
            {
                
                // Si de la rueda w ya se encontro el offset, se salta la iteracion actual para evitar repetir operaciones innecesarias
                
                if(offset[w] != 0) 
                { 
                    continue;
                }
                
                // A la rueda w se le hace un giro en sentido contrario, si la rueda w es la dueña original del simbolo, entonces al hacerla girar al reves la distancia dist
                // las dos ruedas se van a desalinear eliminando el color duplicado y agregando otro color a distinctSymbols
                
                sm.spin(w, -dist);
                k = sm.distinctSymbols();
                
                // Este condicional representa el caso mencionado anteriormente en el que la cantidad de simbolos distintos vuelve a ser n
                
                if(k == n) 
                {
                    
                    // Se guarda el offset y se deja la rueda w en su posicion original
                    
                    offset[w] = dist;
                    sm.spin(w, dist);
                    k = sm.distinctSymbols();
                    break;
                } 
                else 
                {
                    
                    // Se deja a w en su posicion original
                    
                    sm.spin(w, dist);
                }
            }
        }
        
        // Se devuelve la primera rueda a su estado inicial 
        
        sm.spin(1, 1);
        
        // Se empieza a acomodar cada rueda a partir de la segunda con respecto al offset de cada una 

        for(int w = 2; w <= n; w++) 
        {
                sm.spin(w, -offset[w]);
                k = sm.distinctSymbols();
                
                if(k == 1) 
                {
                    return;
                }
        }   
    }
}