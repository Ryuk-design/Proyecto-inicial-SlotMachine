package SlotMachine_Package;
import java.util.ArrayList;

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


public class SlotMachineContest
{
    /**
     * Solucion SlotMachineConstest
     * @param n numero de simbolos y ruedas 3 <= n <= 50
     */
    public static int[][] solve(int n)
    {
        if(n < 3 || n > 50)
        {
            return new int[0][0];
        }
        return null;
    }
    
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
            System.out.println(sm.isJackpot());
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
            
            if (!progress) 
            {
                sm.spin(i, 1);
                k = sm.distinctSymbols();
                
                if (k == 1)
                { 
                    return;
                }
            }
        }
        
        // Se crea un arreglo que en cada rueda guarda la distancia del simbolo que contiene con respecto al simbolo de la primera rueda
        // Se crea con un tamaño n + 1 para que la rueda corresponda directamente con la posicion del arreglo
        
        int[] offset = new int[n + 1];
        
        // Se cambia el simbolo de la primera rueda al girarla, lo que genera que la primera rueda pase a tener el simbolo que correspondia originalmente a otra rueda
        
        for (int dist = 1; dist < n; dist++) 
        {
            sm.spin(1, 1);
            k = sm.distinctSymbols();
            
            // Se busca la rueda en la cual coincidio el simbolo, que puede ser desde la segunda rueda en adelante
            
            for (int c = 2; c <= n; c++) 
            {
                
                // Si de la rueda c ya se encontro el offset, se salta la iteracion actual para evitar repetir operaciones innecesarias
                
                if (offset[c] != 0) 
                { 
                    continue;
                }
                
                // A la rueda c se le hace un giro en sentido contrario, si la rueda c es la dueña original del simbolo, entonces al hacerla girar al reves la distancia dist
                // las dos ruedas se van a desalinear eliminando el color duplicado y agregando otro color a distinctSymbols
                
                sm.spin(c, -dist);
                k = sm.distinctSymbols();
                
                // Este condicional representa el caso mencionado anteriormente en el que la cantidad de simbolos distintos vuelve a ser n
                
                if (k == n) 
                {
                    
                    // Se guarda el offset y se deja la rueda c en su posicion original
                    
                    offset[c] = dist;
                    sm.spin(c, dist);
                    k = sm.distinctSymbols();
                    break;
                } 
                else 
                {
                    
                    // Se deja a c en su posicion original
                    
                    sm.spin(c, dist);
                    k = sm.distinctSymbols();
                }
            }
        }
        
        // Se devuelve la primera rueda a su estado inicial 
        
        sm.spin(1, 1);
        k = sm.distinctSymbols();
        
        // Se empieza a acomodar cada rueda a partir de la segunda con respecto al offset de cada una 

        for (int c = 2; c <= n; c++) 
        {
                sm.spin(c, -offset[c]);
                k = sm.distinctSymbols();
                
                if (k == 1) 
                {
                    return;
                }
        }   
    }
}