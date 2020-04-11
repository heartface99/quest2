import java.util.*;

public class PlayQuestOfLegends extends Play{
	private static ArrayList<Character> heroes;
    private static ArrayList<Monster> monsters;
    private static NexusBoard playingboard;
    protected static MainMarket market = new MainMarket();

    //introduction to set up game by choosing your heros. 
    public static void start(){
        //previously introduction setup
        num_hero = 3;
        System.out.println("Welcome to the Quest of Legend.");
        System.out.println("In the middle of chaos, you were choosen to save the world.");
        System.out.println("You will lead the team of heroes to bring peace for the kingdom");
        System.out.println("Tell me us your name, before your adventure begins");
        System.out.println();
        
        System.out.print("Please enter your name:");
        String name = scannername.next();
        currentplayer = new Player(name,'O');
        System.out.println();
        System.out.println(name + " ,we have choosen some of the brightest strongest heroes in the whole kingdom to aid you!");
        System.out.println("Since you have 3 nexus to defend from the monster, please choose your 3 heros!");
        System.out.println("You might want to decide who to pick with you base on their class!");
        System.out.println("Warriors are favored on the strength and the agility.");
        System.out.println("Sorcerers are favored on the dexterity and the agility.");
        System.out.println("Paladins are favored on the strength and the dexterity.");
        System.out.println("Here are the list of possible heros and their information! Remember you can choose at max " + num_hero + " hero(es)!");
        System.out.println();
        possiblehero.printlist();
        System.out.println();
        adding_hero_final(num_hero);
        System.out.println();
        System.out.println("Here is the current state of all your memebers of the team!");
        currentplayer.printlist();
        System.out.println();
        System.out.println("Your adventure will begin now!");
        System.out.println("Move using W(up),S(down),A(left),D(right)");
        System.out.println("You cannot move to terrain(X), but can visit market(M) to buy and sell stuff. All others are the wild that will have a chance to spawn monsters! Beware!");
        System.out.println("Check your hero stats using T");
        System.out.println("Check inventory to equip and use items using I");
        System.out.println("To teleport to a new map use M");
        System.out.println("Close the game using Q");
        scannername.nextLine();
        //set up the board, monster, and heros
        playingboard= new NexusBoard(8,8); 
        monsters=possiblemoster.matchLevel(currentplayer.returnmaxlevel(),num_hero);
        heroes= currentplayer.returnHerolist();
        // initalizeMonsterPostion();
        initalizeHeroPostion(8);
        initalizeMonsterPostion();
        playingboard.printBoard();
        play();
    }

    //check if you type in the instructions that is allowed in game.
    public static String valid_move(String x) {
        // System.out.println((x.equals("w")==false|| x.equals("a")==false|| x.equals("s")==false ||x.equals("d")==false||x.equals("q")==false||x.equals("t")==false||x.equals("e")==false));
        // System.out.println(true||false);
        while(x.equals("w")==false&&x.equals("a")==false&& x.equals("s")==false &&x.equals("d")==false){
        //  while(x.equals("a")==false||x.equals("w")==false){
            System.out.print("Invalid input! Enter your moves: ");

            x= scannername.nextLine();
            x=x.toLowerCase();
        }
        return x;
    }


    //after user choose what to do next, you elaborate on what their choices can be
    public static void chosenMove(Character curr, int i){
        //player choose to make a move. 
        boolean loop= true;
        switch (i) {
            case 1:
                while(loop){
                System.out.println("Make a move(w,s,a,d): ");
                String x= scannername.nextLine();
                String instruction = valid_move(x);
                loop = makeMove(x,curr);
                }
                playingboard.printBoard();
                return;
            case 2:
                attack(curr);
                return;
            case 3:
                cast_spell(curr);
                return;
            case 4:
                inventory(curr);
                return;
            case 5:
                return_base(curr);
                return;
            case 6:
                System.out.println("not implemented yet");
                return;
            case 7:
                currentplayer.printlist();

                output_choice(curr);
            case 8:
                possiblemoster.printMonster(monsters);
                output_choice(curr);
                return;
            case 9:

                Marketplace(curr);

    }
}

    //allowing players to buy potion
    public static void buyPotion(Character currentchar){
        MainMarket market= playingboard.getMarket();
        String yesno; 
        System.out.println(currentchar.getName()+" ,here is all the potions sold in the shop.Some potions are only for temporary effect and will effect will be gone after battle.");
        market.printPotion();
        System.out.print("Enter a number from 0-" + (market.returnPotionStore().size())+" to buy: ");
        scannername.nextLine();
        int y= isInt();
         while (valid_input2(y,market.returnPotionStore().size())== false){
                System.out.print("Incorrect input! Please choose a correct number: ");
                scannername.nextLine();
                y= isInt();
            }
            if(y==(market.returnPotionStore().size())){
                Marketplace(currentchar);
            }
            else if (y==(market.returnWeaponStore().size())==false){
                currentchar.buyPotion(market.getPotion(y));
                scannername.nextLine();
                System.out.println("Would you like to buy another potion? (y/n):");
                yesno = scannername.nextLine();
                yesno=yesno.toLowerCase();
                while(yesno.equals("y")==false&& yesno.equals("n")==false){
                    System.out.println("Invalid input! Enter (y/n):");
                    yesno = scannername.nextLine();
                    yesno=yesno.toLowerCase();
                }
                if(yesno.equals("y")){
                    buyPotion(currentchar);
                    }
                else if (yesno.equals("n")){
                    Marketplace(currentchar);
                }
            }
        }
    //allowing players to buy weapon
    public static void buyWeapon(Character currentchar){
        MainMarket market= playingboard.getMarket();
        String yesno; 
        System.out.println(currentchar.getName()+" ,here is all the weapons sold in the shop.");
        market.printWeapon();
        System.out.println("Enter a number from 0-" + (market.returnWeaponStore().size())+" to buy: ");
        scannername.nextLine();
        int y= isInt();
         while (valid_input2(y,market.returnWeaponStore().size())== false){
                System.out.println("Incorrect input! Please choose a correct number: ");
                scannername.nextLine();
                y= isInt();
            }
            if(y==(market.returnWeaponStore().size())){
                Marketplace(currentchar);
            }
            else if (y==(market.returnWeaponStore().size())==false){
                currentchar.buyWeapon(market.getWeapon(y));
                scannername.nextLine();
                System.out.println("Would you like to buy another weapon? (y/n):");
                yesno = scannername.nextLine();
                yesno=yesno.toLowerCase();
                while(yesno.equals("y")==false&& yesno.equals("n")==false){
                    System.out.println("Invalid input! Enter (y/n):");
                    yesno = scannername.nextLine();
                    yesno=yesno.toLowerCase();
                }
                if(yesno.equals("y")){
                    buyWeapon(currentchar);
                    }
                else if (yesno.equals("n")){
                    Marketplace(currentchar);
                }
            }
        }


    //allowing players to buy armor
    public static void buyArmor(Character currentchar){
        MainMarket market= playingboard.getMarket();
        String yesno; 
        System.out.println(currentchar.getName()+" ,here is all the armors sold in the shop.");
        market.printArmor();
        System.out.println("Enter a number from 0-" + (market.returnArmorStore().size())+" to buy: ");
        scannername.nextLine();
        int y= isInt();
         while (valid_input2(y,market.returnArmorStore().size())== false){
                System.out.println("Incorrect input! Please choose a correct number: ");
                scannername.nextLine();
                y= isInt();
            }
            if(y==(market.returnArmorStore().size())){
                Marketplace(currentchar);
            }
            else if (y==(market.returnArmorStore().size())==false){
                currentchar.buyArmor(market.getArmor(y));
                scannername.nextLine();
                System.out.println("Would you like to buy another armor? (y/n):");
                yesno = scannername.nextLine();
                yesno=yesno.toLowerCase();
                while(yesno.equals("y")==false&& yesno.equals("n")==false){
                    System.out.println("Invalid input! Enter (y/n):");
                    yesno = scannername.nextLine();
                    yesno=yesno.toLowerCase();
                }
                if(yesno.equals("y")){
                    buyArmor(currentchar);
                    }
                else if (yesno.equals("n")){
                    Marketplace(currentchar);
                }
            }
        }
        //allowing player to buy spell
        public static void buySpell(Character currentchar){
            MainMarket market= playingboard.getMarket();
            String yesno; 
            System.out.println(currentchar.getName()+" ,here is all the spells sold in the shop.");
            System.out.println("Ice spell reduce enemey damage, fire spell reduce their defense, lightning spell reduce their dodge change!");
            market.printSpell();
            System.out.println("Enter a number from 0-" + (market.returnSpellStore().size())+" to buy: ");
            scannername.nextLine();
            int y= isInt();
             while (valid_input2(y,market.returnSpellStore().size())== false){
                    System.out.println("Incorrect input! Please choose a correct number: ");
                    scannername.nextLine();
                    y= isInt();
                }
                if(y==(market.returnSpellStore().size())){
                    Marketplace(currentchar);
                }
                else if (y==(market.returnSpellStore().size())==false){
                    currentchar.buySpell(market.getSpell(y));
                    scannername.nextLine();
                    System.out.println("Would you like to buy another spell? (y/n):");
                    yesno = scannername.nextLine();
                    yesno=yesno.toLowerCase();
                    while(yesno.equals("y")==false&& yesno.equals("n")==false){
                        System.out.println("Invalid input! Enter (y/n):");
                        yesno = scannername.nextLine();
                        yesno=yesno.toLowerCase();
                    }
                    if(yesno.equals("y")){
                        buySpell(currentchar);
                        }
                    else if (yesno.equals("n")){
                        Marketplace(currentchar);
                    }
                }
            }
        //allowing player to sell armor
        public static void sellArmor(Character currentchar){
                String yesno; 
                System.out.println(currentchar.getName()+" ,here is all the armors owned by this hero.");
                currentchar.printArmorSale();
                System.out.println("Enter a number from 0-" + (currentchar.returnArmorStorage().size())+" to sell: ");
                scannername.nextLine();
                int y= isInt();
                 while (valid_input2(y,currentchar.returnArmorStorage().size())== false){
                        System.out.println("Incorrect input! Please choose a correct number: ");
                        scannername.nextLine();
                        y= isInt();
                    }
                    if(y==(currentchar.returnArmorStorage().size())){
                        Marketplace(currentchar);
                    }
                    else if (y==(currentchar.returnArmorStorage().size())==false){
                        currentchar.sellArmor(currentchar.getArmor(y));
                        scannername.nextLine();
                        System.out.println("Would you like to sell another armor? (y/n):");
                        yesno = scannername.nextLine();
                        yesno=yesno.toLowerCase();
                        while(yesno.equals("y")==false&& yesno.equals("n")==false){
                            System.out.println("Invalid input! Enter (y/n):");
                            yesno = scannername.nextLine();
                            yesno=yesno.toLowerCase();
                        }
                        if(yesno.equals("y")){
                            sellArmor(currentchar);
                            }
                        else if (yesno.equals("n")){
                            Marketplace(currentchar);
                        }
                    }
                }
        
            //allowing player to sell potion
            public static void sellPotion(Character currentchar){
            
                    String yesno; 
                    System.out.println(currentchar.getName()+" ,here is all the potions own by the hero.");
                    currentchar.printPotionSale();
                    System.out.println("Enter a number from 0-" + (currentchar.returnPotionStorage().size())+" to sell: ");
                    scannername.nextLine();
                    int y= isInt();
                     while (valid_input2(y,currentchar.returnPotionStorage().size())== false){
                            System.out.println("Incorrect input! Please choose a correct number: ");
                            scannername.nextLine();
                            y= isInt();
                        }
                        if(y==(currentchar.returnPotionStorage().size())){
                            Marketplace(currentchar);
                        }
                        else if (y==(currentchar.returnPotionStorage().size())==false){
                            currentchar.sellPotion(currentchar.getPotion(y));
                            scannername.nextLine();
                            System.out.println("Would you like to sell another potion? (y/n):");
                            yesno = scannername.nextLine();
                            yesno=yesno.toLowerCase();
                            while(yesno.equals("y")==false&& yesno.equals("n")==false){
                                System.out.println("Invalid input! Enter (y/n):");
                                yesno = scannername.nextLine();
                                yesno=yesno.toLowerCase();
                            }
                            if(yesno.equals("y")){
                                sellPotion(currentchar);
                                }
                            else if (yesno.equals("n")){
                                Marketplace(currentchar);
                            }
                        }
                    }
        //allowing player to sell weapon
        public static void sellWeapon(Character currentchar){
                    
                    String yesno; 
                    System.out.println(currentchar.getName()+" ,here is all the weapons owned by the hero.");
                    currentchar.printWeaponSale();
                    System.out.println("Enter a number from 0-" + (currentchar.returnWeaponStorage().size())+" to sell: ");
                    scannername.nextLine();
                    int y= isInt();
                     while (valid_input2(y,currentchar.returnWeaponStorage().size())== false){
                            System.out.println("Incorrect input! Please choose a correct number: ");
                            scannername.nextLine();
                            y= isInt();
                        }
                        if(y==(currentchar.returnWeaponStorage().size())){
                            Marketplace(currentchar);
                        }
                        else if (y==(currentchar.returnWeaponStorage().size())==false){
                            currentchar.sellWeapon(currentchar.getWeapon(y));
                            scannername.nextLine();
                            System.out.println("Would you like to sell another weapon? (y/n):");
                            yesno = scannername.nextLine();
                            yesno=yesno.toLowerCase();
                            while(yesno.equals("y")==false&& yesno.equals("n")==false){
                                System.out.println("Invalid input! Enter (y/n):");
                                yesno = scannername.nextLine();
                                yesno=yesno.toLowerCase();
                            }
                            if(yesno.equals("y")){
                                sellWeapon(currentchar);
                                }
                            else if (yesno.equals("n")){
                                Marketplace(currentchar);
                            }
                        }
                    }
    //allwoing player to sell spell
    public static void sellSpell(Character currentchar){
           
            String yesno; 
            System.out.println(currentchar.getName()+" ,here is all the spells own by the hero.");

            currentchar.printSpellSale();
            System.out.println("Enter a number from 0-" + (currentchar.returnSpellStorage().size())+" to sell: ");
            scannername.nextLine();
            int y= isInt();
            while (valid_input2(y,currentchar.returnSpellStorage().size())== false){
                    System.out.println("Incorrect input! Please choose a correct number: ");
                    scannername.nextLine();
                    y= isInt();
                            }
                    if(y==(currentchar.returnSpellStorage().size())){
                                Marketplace(currentchar);
                            }
                    else if (y==(currentchar.returnSpellStorage().size())==false){
                        currentchar.sellSpell(currentchar.getSpell(y));
                        scannername.nextLine();
                        System.out.println("Would you like to sell another spell? (y/n):");
                        yesno = scannername.nextLine();
                        yesno=yesno.toLowerCase();
                        while(yesno.equals("y")==false&& yesno.equals("n")==false){
                            System.out.println("Invalid input! Enter (y/n):");
                            yesno = scannername.nextLine();
                            yesno=yesno.toLowerCase();
                                }
                            if(yesno.equals("y")){
                                sellSpell(currentchar);
                                    }
                            else if (yesno.equals("n")){
                                    Marketplace(currentchar);
                                }
                            }
                        }
    //players access different inventory to sell their item
    public static void sell(int x, Character currentchar){
        if(x==0){
            sellArmor(currentchar);
        }
        else if(x==1){
            sellWeapon(currentchar);
        }
        else if (x==2){
            sellPotion(currentchar);
        }
        else if(x==3){
            sellSpell(currentchar);
        }


    }
 


    //acessing the market place only if the hero is at the market!
    //player can choose to buy or sell in the market place. 
    public static void Marketplace(Character currentchar){
        if(playingboard.check_tile(currentchar.row,currentchar.col)=='N'){
        
        int x;
        System.out.println(currentchar.getName()+" is now looking at the shop! Is " + currentchar.getName()+ " interested in buying or selling?");
        System.out.println(currentchar.getName()+" currently has " + currentchar.getMoney() +" gold!");
        System.out.println("0) Buy");
        System.out.println("1) Sell");
        System.out.println("2) Quit");
        System.out.println("Enter a number from 0-2: ");
        
        x= isInt();
        while (valid_input2(x,2)== false){
            System.out.println("Incorrect input! Please choose a correct number: ");
            scannername.nextLine();
            x= isInt();
        }
        
        if(x==0){

        System.out.println();
        System.out.println("0) Armor Shop");
        System.out.println("1) Weapon Shop");
        System.out.println("2) Potion Shop");
        System.out.println("3) Spell Shop");
        System.out.println("4) Quit");
        System.out.println("Enter a number from 0-4: ");
        scannername.nextLine();
        x=isInt();
        while (valid_input2(x,4)== false){
            System.out.println("Incorrect input! Please choose a correct number: ");
            scannername.nextLine();
            x= isInt();
        }
        if(x== 4){
            Marketplace(currentchar);
        }
        else{
        buy(x,currentchar);
        }
    }

        else if(x==1){
            System.out.println();
            System.out.println("0) Sell Armor ");
            System.out.println("1) Sell Weapon ");
            System.out.println("2) Sell Potion ");
            System.out.println("3) Sell Spell");
            System.out.println("4) Quit");
            System.out.println("Enter a number from 0-4: ");
            scannername.nextLine();
            x=isInt();
            while (valid_input2(x,4)== false){
                System.out.println("Incorrect input! Please choose a correct number: ");
                scannername.nextLine();
                x= isInt();
            }
            if(x== 4){
               output_choice(currentchar);;
            }
            else{
            sell(x,currentchar);
        }
    }
    //if player quits they use up their move
    else if(x==2){
        return;
    }
    }
    else{
        System.out.println("You can only access market at the nexus! Choose another move!");
        output_choice(currentchar);
    }
}

//allow user to equip weapons for a hero
public static void EquipWeapon(Character currentchar){
    String yesno; 
    System.out.println(currentchar.getName()+" ,here is all the weapons owned by the hero.");
    currentchar.printWeaponSale();
    System.out.println("Enter a number from 0-" + (currentchar.returnWeaponStorage().size())+" to equip: ");
    scannername.nextLine();
    int y= isInt();
     while (valid_input2(y,currentchar.returnWeaponStorage().size())== false){
            System.out.println("Incorrect input! Please choose a correct number: ");
            scannername.nextLine();
            y= isInt();
        }
        if(y==(currentchar.returnWeaponStorage().size())){
            inventory(currentchar);
        }
        else if (y==(currentchar.returnWeaponStorage().size())==false){
            currentchar.equipWeapon(currentchar.getWeapon(y));
            scannername.nextLine();
            System.out.println("Would you like to equip a different weapon instead? (y/n):");
            yesno = scannername.nextLine();
            yesno=yesno.toLowerCase();
            while(yesno.equals("y")==false&& yesno.equals("n")==false){
                System.out.println("Invalid input! Enter (y/n):");
                yesno = scannername.nextLine();
                yesno=yesno.toLowerCase();
            }
            if(yesno.equals("y")){
                EquipWeapon(currentchar);
                }
            else if (yesno.equals("n")){
                inventory(currentchar);
            }
        }
}

//equip spell of a hero
public static void EquipSpell(Character currentchar){
    String yesno; 
    System.out.println(currentchar.getName()+" ,here is all the spells own by the hero.");

    currentchar.printSpellSale();
    System.out.println("Enter a number from 0-" + (currentchar.returnSpellStorage().size())+" to equip: ");
    scannername.nextLine();
    int y= isInt();
    while (valid_input2(y,currentchar.returnSpellStorage().size())== false){
            System.out.println("Incorrect input! Please choose a correct number: ");
            scannername.nextLine();
            y= isInt();
                    }
            if(y==(currentchar.returnSpellStorage().size())){
                        inventory(currentchar);
                    }
            else if (y==(currentchar.returnSpellStorage().size())==false){
                currentchar.equipSpell(currentchar.getSpell(y));
                scannername.nextLine();
                System.out.println("Would you like to equip another spell instead? (y/n):");
                yesno = scannername.nextLine();
                yesno=yesno.toLowerCase();
                while(yesno.equals("y")==false&& yesno.equals("n")==false){
                    System.out.println("Invalid input! Enter (y/n):");
                    yesno = scannername.nextLine();
                    yesno=yesno.toLowerCase();
                        }
                    if(yesno.equals("y")){
                        EquipSpell(currentchar);
                            }
                    else if (yesno.equals("n")){
                            inventory(currentchar);
                        }
                    }
                }

//equip armor for a hero
public static void EquipArmor(Character currentchar){
    String yesno; 
    System.out.println(currentchar.getName()+" ,here is all the armors owned by this hero.");
    currentchar.printArmorSale();
    System.out.println("Enter a number from 0-" + (currentchar.returnArmorStorage().size())+" to equip: ");
    scannername.nextLine();
    int y= isInt();
     while (valid_input2(y,currentchar.returnArmorStorage().size())== false){
            System.out.println("Incorrect input! Please choose a correct number: ");
            scannername.nextLine();
            y= isInt();
        }
        if(y==(currentchar.returnArmorStorage().size())){
            inventory(currentchar);
        }
        else if (y==(currentchar.returnArmorStorage().size())==false){
            currentchar.equipArmor(currentchar.getArmor(y));
            scannername.nextLine();
            System.out.println("Would you like to equip a different armor instead? (y/n):");
            yesno = scannername.nextLine();
            yesno=yesno.toLowerCase();
            while(yesno.equals("y")==false&& yesno.equals("n")==false){
                System.out.println("Invalid input! Enter (y/n):");
                yesno = scannername.nextLine();
                yesno=yesno.toLowerCase();
            }
            if(yesno.equals("y")){
                EquipArmor(currentchar);
                }
            else if (yesno.equals("n")){
                inventory(currentchar);
            }
        }
}


//allow a hero to use potion
public static void usePotion(Character currentchar){
    String yesno; 
    System.out.println(currentchar.getName()+" ,here is all the potions own by the hero.");
    currentchar.printPotionSale();
    System.out.println("Enter a number from 0-" + (currentchar.returnPotionStorage().size())+" to use: ");
    scannername.nextLine();
    int y= isInt();
     while (valid_input2(y,currentchar.returnPotionStorage().size())== false){
            System.out.println("Incorrect input! Please choose a correct number: ");
            scannername.nextLine();
            y= isInt();
        }
        if(y==(currentchar.returnPotionStorage().size())){
            inventory(currentchar);
        }
        else if (y==(currentchar.returnPotionStorage().size())==false){
            currentchar.usePotion(currentchar.getPotion(y));
            scannername.nextLine();
            System.out.println("Would you like to use another potion? (y/n):");
            yesno = scannername.nextLine();
            yesno=yesno.toLowerCase();
            while(yesno.equals("y")==false&& yesno.equals("n")==false){
                System.out.println("Invalid input! Enter (y/n):");
                yesno = scannername.nextLine();
                yesno=yesno.toLowerCase();
            }
            if(yesno.equals("y")){
                usePotion(currentchar);
                }
            else if (yesno.equals("n")){
               inventory(currentchar);
            }
        }
    }
    


//access the inventory of the character 
    public static void inventory(Character currentchar){
        System.out.println(currentchar.getName()+ " can now access the inventory.");
        System.out.println("What operations to perform?");
        System.out.println("0) Change Armor");
        System.out.println("1) Change Weapon");
        System.out.println("2) Use Potion");
        System.out.println("3) Change Spell");
        System.out.println("4) Unequip current Armor");
        System.out.println("5) Unequip current Weapon");
        System.out.println("6) Unequip current Spell");
        System.out.println("7) Quit");
        System.out.println("Enter a number from 0-7: ");
       
        int x;
        x=isInt();
        while (valid_input2(x,7)== false){
            System.out.println("Incorrect input! Please choose a correct number: ");
            scannername.nextLine();
            x= isInt();
        }
        if (x== 7){
            scannername.nextLine();
            return;
            // actual_game();
        }
        else{
            Equip(x,currentchar);
        }
    }

    
    //ask player which monster they want to attack 
    public static void attack(Character curr){
        ArrayList <Monster> monster_inrange= new ArrayList <Monster> ();
        monster_inrange= playingboard.monster_in_range(curr.row,curr.col);
        if(monster_inrange.size() >0){
            System.out.println("Which enemey do you want to target?");
      
            possiblemoster.printMonster(monster_inrange);
            
            int x= isInt();
            scannername.nextLine();
            while (valid_input2(x,monster_inrange.size()-1)== false){
                System.out.println("Incorrect input! Please choose a correct number: ");
                // scannername.nextLine();
                x= isInt();
            }
            Monster choosenmonster = monsters.get(x);
            curr.attack(choosenmonster);

        }
        else{
            System.out.println("There is no monster to attack! Please choose a different move!");
            output_choice(curr);
        }
    }

     //ask player which monster they want to cast a spell on
     public static void cast_spell (Character curr){
        ArrayList <Monster> monster_inrange= new ArrayList <Monster> ();
        monster_inrange= playingboard.monster_in_range(curr.row,curr.col);
        if(monster_inrange.size() >0){
            System.out.println("Which enemey do you want to target?");
      
            possiblemoster.printMonster(monster_inrange);
            
            int x= isInt();
            scannername.nextLine();
            while (valid_input2(x,monster_inrange.size()-1)== false){
                System.out.println("Incorrect input! Please choose a correct number: ");
                // scannername.nextLine();
                x= isInt();
            }
            Monster choosenmonster = monsters.get(x);
            curr.magicattack(choosenmonster);

        }
        else{
            System.out.println("There is no monster to attack! Please choose a different move!");
            output_choice(curr);
        }
    }
    //moves the player by w,a,s,d and check if that move is viable.

    public static boolean makeMove(String instruction, Character curr){
        // makes the move on the board according to the instructions and the current monster
        int currentrow=curr.row;
        int currentcol=curr.col;
        int nextrow;
        int nextcol;
        if(instruction.equals("a")){
            nextrow=currentrow-1;
            nextcol= currentcol;
        
            if(playingboard.movingtoempyspace(nextrow,nextcol,curr)=="OK"){
                curr.row=(nextrow);
                curr.col=(nextcol);
                playingboard.printBoard();
                return false;
            }

            else if(playingboard.movingtoempyspace(nextrow,nextcol,curr)=="X"){ 
                System.out.println("Cannot move there!");
                return true;
            }
        }

        if(instruction.equals("w")){
            nextrow=currentrow;
            nextcol= currentcol-1;
            System.out.println(curr.row+" "+curr.col);
            System.out.println(nextrow+" "+ nextcol);
            // System.out.println((playingboard.movingtoempyspace(nextrow,nextcol,curr)));
            if(playingboard.movingtoempyspace(nextrow,nextcol,curr)=="OK"){
                curr.row=(nextrow);
                curr.col=(nextcol);
                playingboard.printBoard();
            return false;
            
            }
           

            
            else if(playingboard.movingtoempyspace(nextrow,nextcol,curr)=="X"){
                System.out.println("Cannot move there!");
                return true;
            }
            
        }
        if(instruction.equals("s")){
            nextrow=currentrow;
            nextcol= currentcol+1;
        
            if(playingboard.movingtoempyspace(nextrow,nextcol,curr)=="OK"){
                curr.row=(nextrow);
                curr.col=(nextcol);
                playingboard.printBoard();
            return false;
            
            }
           

            
            else if(playingboard.movingtoempyspace(nextrow,nextcol,curr)=="X"){
                System.out.println("Cannot move there!");
                return true;
            }
        }
            
            if(instruction.equals("d")){
                nextrow=currentrow+1;
                nextcol= currentcol;
            
                if(playingboard.movingtoempyspace(nextrow,nextcol,curr)=="OK"){
                    curr.row=(nextrow);
                    curr.col=(nextcol);
                    playingboard.printBoard();
                   
                return false;
                
                }
               
    
                
                else if(playingboard.movingtoempyspace(nextrow,nextcol,curr)=="X"){
                    System.out.println("Cannot move there!");
                    return true;
                }
            }
        return true;
        }
    
    //return the player to base(base on their lane). 
    public static void return_base(Character curr){
        int base_row = curr.row;
        int base_col = 7;

        if(playingboard.movingtoempyspace(base_row,base_col,curr)== "OK"){
            curr.row=(base_row);
            curr.col=(base_col);
            playingboard.printBoard();
            return;

        }
        else{
            System.out.println("There is someone at base already!");
            output_choice(curr);
        }


    }

    //output the move a player can choose
    public static void output_choice(Character curr){
        int x;
        System.out.println("Enter moves for "+ curr.getName()+ "("+curr.getPieceName()+")" +" : ");
        System.out.println("1) Move");
        System.out.println("2) Attack");
        System.out.println("3) Cast spell");
        System.out.println("4) Use inventory");
        System.out.println("5) Return to base");
        System.out.println("6) Teleport to another lane");
        System.out.println("7) Check hero stats ");
        System.out.println("8) Check monster stats ");
        System.out.println("9) Access Market");
        System.out.println("Choose a number from 1-9: ");
        x=isInt();

        while (valid_input2(x,9)== false){
        System.out.println("Incorrect input! Please choose a correct number: ");
        scannername.nextLine();
        x= isInt();
       
            } 
        scannername.nextLine();
        chosenMove(curr,x);
    }

    //monster makes a move.
    public static void monster_move(Monster curr){
        //monster will attack if there is a hero in range
    
        ArrayList<Character> hero_inrange= new ArrayList<Character>();
        hero_inrange=playingboard.hero_in_range(curr.row,curr.col);
        if(hero_inrange.size()>0){
        
            Random rand = new Random();
            int val = rand.nextInt(hero_inrange.size());
            Character heroattacked=hero_inrange.get(val);
            curr.attackhero(heroattacked);
        }
        //if no hero in range, it moves forward one block
        else {
          
           
            playingboard.movingtoempyspace(curr.row, curr.col+1,curr);
            curr.col=curr.col+1;
            playingboard.printBoard();
        }

    }
	public static void play(){
        // previously "actual_game"
        //ask the user for their instructions on what they would like to do next
        char celltype;
        int count = 1;
        int x;
        boolean won=false;
        while(playingboard.win()== false){
            for(int i = 0; i <heroes.size(); i++){
              Character curr= heroes.get(i);
                int row= curr.row;
                int col= curr.col;
            
            //check what tile the hero is at.
                celltype=playingboard.check_tile(row, col);
            //check if there is enemy ahead or beside 

                output_choice(curr);
                if(playingboard.win()==true){
                    won=true;
                    break;
                }
            }
            
            if(won== false){
                for(int i = 0; i <monsters.size(); i++){
                     System.out.println("here,");
                      Monster currmonster= monsters.get(i);
                      monster_move(currmonster);

            }
            playingboard.printBoard();
            }
    }}


    //initialzie the starting postion of HERO
    public static void initalizeHeroPostion(int c){
        Character_monster curr;
        int counter=0;
        for(int i = 0; i <heroes.size(); i++){
          curr= heroes.get(i);
          curr.setpiecename("H"+ (i+1));
          curr.setrow(counter);
          curr.setcol(c-1);
          counter+=3;
          set_postion(curr,curr.row,curr.col);
    	}
	}

    //set the postion of monster and heros on the board given the row and col
    public static void set_postion(Character_monster current,int row, int col){

        playingboard.move(row,col,current);

    }

	//intitlize the starting positon of Monster 
    public static void initalizeMonsterPostion(){
        int counter=0;
        int lane=0;
        Character_monster curr;
        System.out.println(monsters.size());
        for(int i = 0; i <monsters.size(); i++){
          curr = monsters.get(i);
          curr.setpiecename("M"+ (i+1));
          curr.setrow(counter);
          curr.setcol(lane);
          counter+=3;
          set_postion(curr, curr.row, curr.col);
    	}
	}
}
