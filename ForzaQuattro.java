class ForzaQuattro {
    //----------------- MENU -----------------
    static void menu(int[][] mat, int turn)
    {
        int key;
        
        //stampo grafica + input
        do
        {
            BasicLib.cls();
            System.out.println("+--------- 🏠 MENU 🏠 ----------+");
            System.out.println("|  [1] Player 👤 VS Player 👤   |");
            System.out.println("|  [2] Player 👤 VS Computer 💻 |");
            System.out.println("+--------- 🏠 MENU 🏠 ----------+");
            key = Leggi.unInt();
        }
        while(key < 1 || key > 2);
        
        BasicLib.cls();
        stampaMat(mat);
        System.out.print("\n⮞⮞ Turno: " + turn + " ⮜⮜");
        switch(key)
        {
            case 1:{
                vsGiocatore(mat, turn);
                break;
            }
            
            case 2:{
                vsComputer(mat, turn);
                break;
            }
        }
    }
    
    //----------------- MAIN VS PLAYER -----------------
    static void vsGiocatore(int[][] mat, int turn)
    {
        int win;
        
        //win = 1 --> vittoria p1 / win = 2 --> vittoria p2
        win = 0;
        while(win == 0)
        {
            win = controlloPed(mat, turn);
            stampaMat(mat);
            
            //cambio turno
            switch(turn)
            {
                case 1:{
                    turn = 2;
                    break;
                }
                
                case 2:{
                    turn = 1;
                    break;
                }
            }
            
            System.out.print("\n⮞⮞ Turno: " + turn + " ⮜⮜");
        }
        
        BasicLib.cls();
        stampaMat(mat);
        System.out.print("\n🌟IL GIOCATORE " + win + " HA VINTO!🌟");
    }
    
    //----------------- MAIN VS PC -----------------
    static void vsComputer(int[][] mat, int turn)
    {
        controlloAI();
    }
    
    //----------------- STAMPA MAT -----------------
    static void stampaMat(int[][] mat) 
    {
        int i, j, p, mod;
        
        //stampo indice colonna + 1
        System.out.print(" ");
        for(i = 1; i < mat[0].length + 1; i++)
        {
            System.out.print(" " + i + "  ");
        }
        System.out.println();
        
        p = 0;
        mod = 0;
        for(i = 0; i < (mat.length * 2) + 1; i++)
        {
            for(j = 0; j < mat[0].length + 1; j++)
            {
                if(mod == 0)
                {
                    if(j == 0)
                    {
                        System.out.print("+");
                    }
                    else
                    {
                        System.out.print("---+");
                    }
                }
                else
                {
                    if(j == 0)
                    {
                        System.out.print("|");
                    }
                    else
                    {
                        if(i > 1)
                        {
                           p = (i / 2);
                        }
                        
                        if(mat[p][j - 1] != 0)
                        {
                            if(mat[p][j - 1] == 1)
                            {
                                System.out.print(" ○ |");
                            }
                            else
                            {
                                System.out.print(" ● |");
                            }
                            //System.out.print(" " + mat[p][j - 1] + " |");
                        }
                        else
                        {
                            System.out.print("   |");
                        }
                    }
                }
            }
            System.out.print("\n");
            
            //cambio modalità cornice riga/colonna
            switch(mod)
            {
                case 0:{
                    mod = 1;
                    break;
                }
                
                case 1:{
                    mod = 0;
                    break;
                }
            }
        }
    }
    
    //----------------- AGGIORNA MAT -----------------
    static int aggiornaMat(int[][] mat, int turn, int pos)
    {
        int win;
        
        for(int i = mat.length - 1; i > -1; i--)
        {
            if(mat[i][pos] == 0)
            {
                mat[i][pos] = turn;
                win = controlloVittoria(mat, turn, i, pos);
                return win;
            }
        }
        
        BasicLib.cls();
        stampaMat(mat);
        System.out.println("\n⚠️Pila piena! Reinserire un valore ⚠️");
        controlloPed(mat, turn);
        
        return 0;
    }
    
    //----------------- CONTROLLO PEDINA -----------------
    static int controlloPed(int[][] mat, int turn)
    {
        int key, win;
        key = Leggi.unInt();
        
        //controllo input
        while(key > mat.length + 1 || key < 1)
        {
            BasicLib.cls();
            stampaMat(mat);
            System.out.println("\n⚠️Valore non valido, reinserire ⚠️");
            key = Leggi.unInt();
            BasicLib.cls();
        }
        win = aggiornaMat(mat, turn, key - 1);
        
        BasicLib.cls();
        
        return win;
    }
    
    //----------------- CONTROLLO VITTORIA -----------------
    static int controlloVittoria(int[][] mat, int turn, int posR, int posC)
    {
        int i, j, x, y, somma;
        char wait;
        
        //controllo oriz
        somma = 1;
        for(i = posC - 1; i > -1 && mat[posR][i] == mat[posR][posC]; i--)
        {
            somma++;
        }
        
        for(j = posC + 1; j < mat.length && mat[posR][j] == mat[posR][posC]; j++)
        {
            somma++;
        }
        
        if(somma == 4)
        {
            return turn;
        }
        
        //controllo vert
        somma = 1;
        for(i = posR - 1; i > -1 && mat[i][posC] == mat[posR][posC]; i--)
        {
            somma++;
        }
        
        for(j = posR + 1; j < mat[0].length - 1 && mat[j][posC] == mat[posR][posC]; j++)
        {
            somma++;
        }
        
        if(somma == 4)
        {
            return turn;
        }
        
        //controllo obl sx
        somma = 1;
        x = posC - 1;
        for(i = posR - 1; (i > -1 && x > -1) && mat[i][x] == mat[posR][posC]; i--)
        {
            somma++;
            x--;
        }
        
        y = posC + 1;
        for(j = posR + 1; (j < mat[0].length - 1 && y < mat.length) && mat[j][y] == mat[posR][posC]; j++)
        {
            somma++;
            y++;
        }
        
        if(somma == 4)
        {
            return turn;
        }
       
        //controllo obl dx
        somma = 1;
        x = posC - 1;
        for(i = posR + 1; (i < mat[0].length - 1 && x > -1) && mat[i][x] == mat[posR][posC]; i++)
        {
            somma++;
            x--;
        }
        
        y = posC + 1;
        for(j = posR - 1; (j > -1 && y < mat.length) && mat[j][y] == mat[posR][posC]; j--)
        {
            somma++;
            y++;
        }
        
        if(somma == 4)
        {
            return turn;
        }
       
        return 0;
    }
    
    static void controlloAI()
    {
        
    }
    
    //----------------- MAIN -----------------
    public static void main(String[] a) {
        int[][] mat;
        int l, turn;
        
        turn = 1;
        l = 6;
        mat = new int[l][l + 1];
        
        menu(mat, turn);
    }
}