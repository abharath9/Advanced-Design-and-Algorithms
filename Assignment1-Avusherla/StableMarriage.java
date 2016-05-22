    /**
     * Created by SONY on 08-10-2015.
     */
    /** Java Program to Implement Gale Shapely algorithm or Stable Marriage Algorithm*/

    import java.io.*;
    import java.util.*;


    /** Class StableMarriage **/
    public class StableMarriage
    {
        //define requires variables and constants
        private int numberOfSubjects, matchCount;
        //list for all men
        private ArrayList<String> men=new ArrayList<String>();
        //list for all women
        private ArrayList<String> women=new ArrayList<String>();
        //list for store all men preferences
        private ArrayList<ArrayList<String>> menPref=new ArrayList<ArrayList<String>>();
        //list for store all women preferences
        private ArrayList<ArrayList<String>> womenPref=new ArrayList<ArrayList<String>>();
        //create a string array for store all the women partners
        private String[] womenStableMatch;
        //create a boolean array for store all the men partners status
        private boolean[] menPairedTo;

        /** Constructor **/
        public StableMarriage(ArrayList<String> m, ArrayList<String> w, ArrayList<ArrayList<String>> mp, ArrayList<ArrayList<String>> wp)
        {
            //initialize all the class variables
            numberOfSubjects = mp.size();
            matchCount = 0;
            men = m;
            women = w;
            menPref = mp;
            womenPref = wp;
            menPairedTo = new boolean[numberOfSubjects];
            womenStableMatch = new String[numberOfSubjects];
            //invoke calcStableMatches to calculate the stable matches
            calcStableMatches();
        }
        /** to calculate the stable matches **/
        private void calcStableMatches()
        {
            int free;
            //check all the women are correctly matched to men
            while (matchCount < numberOfSubjects)
            {
                free=0;
                //check for all the women are free or not
                while(free<numberOfSubjects)
                {
                    //if women is not paired then break
                    if (!menPairedTo[free])
                        break;
                    free++;
                }
                //get the indexes of most preferred and checkwith the current partner and assign a stable match
                for (int i = 0; i < numberOfSubjects && !menPairedTo[free]; i++)
                {
                    //int index = womenIndexOf(menPref.get(free).get(i));
                    //System.out.print(menPref.get(free).get(i));
                    int index=women.indexOf(menPref.get(free).get(i));
                    //check whether women is free or not

                    if (womenStableMatch[index] == null)
                    {
                        womenStableMatch[index] = men.get(free);
                        menPairedTo[free] = true;
                        matchCount++;
                    }
                    //otherwise
                    else
                    {
                        String currentPartner = womenStableMatch[index];
                        //check women preferences between current partner and new partner
                        if (checkForMorePreference(currentPartner, men.get(free), index))
                        {
                            womenStableMatch[index] = men.get(free);
                            menPairedTo[free] = true;
                            //menPairedTo[menIndexOf(currentPartner)] = false;
                            menPairedTo[men.indexOf(currentPartner)]=false;
                        }
                    }
                }
            }
            //print couples
            System.out.println("Couples are : ");
            for (int i = 0; i < numberOfSubjects; i++)
            {
                System.out.println(womenStableMatch[i] +" "+ women.get(i));
            }

        }
        /** function to check if women prefers new partner over old assigned partner **/
        private boolean checkForMorePreference(String presentPartner, String newPartner, int index)
        {
            for (int i = 0; i < numberOfSubjects; i++)
            {
                //check if new partner has the highest priority
                if (womenPref.get(index).get(i).replace(" ","").equals(newPartner.replace(" ", "")))
                    return true;
                //check if current partner has the highest priority
                if (womenPref.get(index).get(i).equals(presentPartner))
                    return false;
            }
            return false;
        }


        /** main function **/
        public static void main(String[] args) throws IOException {

            System.out.println("Stable Marriage Algorithm\n");
           // long startTime = System.currentTimeMillis();

            System.out.println("enter the no of subjects");
            Scanner sc = new Scanner(System.in);
            int subjects = sc.nextInt();
            long startTime = System.currentTimeMillis();

            ArrayList<String> men = new ArrayList<String>();
            ArrayList<String> menDummy = new ArrayList<String>();
            for( int j=0; j<subjects;j++){
                int k=j+1;
                men.add("m" + k);
                menDummy.add("m" + k);
            }



            ArrayList woman= new ArrayList();
            ArrayList womanDummy =  new ArrayList();

            for( int j=0; j<subjects;j++){
                int k=j+1;
                woman.add("w" + k);
                womanDummy.add("w" + k);
            }



            HashMap<String,ArrayList<String>> menPref= new HashMap<String,ArrayList<String>>();
            for(int i=0;i<men.size();i++)
            {
                ArrayList womanDummyTmp=new ArrayList();
                womanDummyTmp=womanDummy;
                Random rnd = new Random();
                int seed = rnd.nextInt();
                rnd.setSeed(seed);
                Collections.shuffle(womanDummyTmp, rnd);

                ArrayList a=new ArrayList();
                for(int m=0;m<womanDummyTmp.size();m++)
                {
                    String x=(String) womanDummyTmp.get(m);
                    a.add(m, x);

                }


                menPref.put(men.get(i), a);
            }



            HashMap womanPref= new HashMap();
            for(int i=0;i<woman.size();i++)
            {
                ArrayList menDummyTemp=new ArrayList();
                menDummyTemp=menDummy;
                Random rnd = new Random();
                int seed = rnd.nextInt();
                rnd.setSeed(seed);
                Collections.shuffle(menDummyTemp, rnd);

                ArrayList a2=new ArrayList();
                for(int m=0;m<menDummyTemp.size();m++)
                {
                    String x=(String) menDummyTemp.get(m);
                    a2.add(m, x);

                }


                womanPref.put(woman.get(i), a2);
            }
            try {
            File file = new File("C:\\Users\\SONY\\Documents\\Avusherla\\ADA\\StableMarriageInput.txt");
            // create a new one if file doesn't exists
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write((Integer.toString(subjects)));
            bw.newLine();
            System.out.println("men preferences");
            for(int i= 0; i < men.size(); i++)
            {
                System.out.println(men.get(i).toString().replace(" ", "") + ":" + menPref.get(men.get(i)).toString().replace("[", "").replace("]", "").replace(" ", ""));
                bw.write(men.get(i).toString().replace(" ", "") + ":" + menPref.get(men.get(i)).toString().replace("[", "").replace("]", "").replace(" ", ""));
                bw.newLine();
            }

            System.out.println("women preferences");
            for(int i=0;i<woman.size();i++)
            {
                System.out.println(woman.get(i).toString().replace(" ", "") + ":" + womanPref.get(woman.get(i)).toString().replace("[", "").replace("]", "").replace(" ", ""));
                bw.write(woman.get(i).toString().replace(" ","")+":"  + womanPref.get(woman.get(i)).toString().replace("[","").replace("]","").replace(" ",""));
                bw.newLine();
            }

            bw.close();
        }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            //read file
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\SONY\\Documents\\Avusherla\\ADA\\StableMarriageInput.txt"));
            String firstLine,input;
            //read the first line
            firstLine = in.readLine();
            //convert first line to integer
            int i = Integer.parseInt(firstLine);
            int subject=i;
            int count=1;
            //define men, women and their preferences variables
            ArrayList<String> m=new ArrayList<String>();
            ArrayList<ArrayList<String>> mp=new ArrayList<ArrayList<String>>();
            ArrayList<String> w=new ArrayList<String>();
            ArrayList<ArrayList<String>> wp=new ArrayList<ArrayList<String>>();
            //extract the men, women details and their preferences from file
            while ((input = in.readLine()) != null )
            {
                count ++;
                //men and their preferences
                if (count > 1 && count <= i+1)
                {
                    //System.out.println(i);
                    //System.out.println(input.substring(0,input.indexOf(":")));
                    m.add(input.substring(0, input.indexOf(":")));
                    String mpTemp=input.substring(input.indexOf(":") + 1, input.length());
                    ArrayList<String> myList = new ArrayList<String>(Arrays.asList(mpTemp.split(",")));
                    mp.add(myList);

                }
                //women and their preferences
                else if (count > i+1 && count <= i+i+1)
                {
                    w.add(input.substring(0,input.indexOf(":")));
                    String mpTemp=input.substring(input.indexOf(":")+1,input.length());
                    ArrayList<String> myList = new ArrayList<String>(Arrays.asList(mpTemp.split(",")));
                    wp.add(myList);
                }
            }
            //invoke StableMarriage class
            StableMarriage sm = new StableMarriage(m, w, mp, wp);
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Total execution time is "+ totalTime+" milliseconds");

        }
    }