import java.util.*;

/**
 * Created by yinzhe on 17/10/16.
 */

class PCB {
    public int ID;
    public int PRIORITY;
    public int CPUTIME;
    public int ALLTIEM;
    public int STARTBLOCK;
    public int BLOCKTIME;
    public String STATE;

    public PCB(int ID, int PRIORITY, int CPUTIME, int ALLTIEM, int STARTBLOCK, int BLOCKTIME, String STATE) {
        this.ID = ID;
        this.PRIORITY = PRIORITY;
        this.CPUTIME = CPUTIME;
        this.ALLTIEM = ALLTIEM;
        this.STARTBLOCK = STARTBLOCK;
        this.BLOCKTIME = BLOCKTIME;
        this.STATE = STATE;
    }

    public int run() {
        PRIORITY = PRIORITY - 3;
        CPUTIME = CPUTIME + 1;
        ALLTIEM = ALLTIEM - 1;
        STARTBLOCK = STARTBLOCK - 1;

        if (STARTBLOCK == 0) {
            STATE = "BLOCK";
            return 1;
        } else if (ALLTIEM == 0) {
            STATE = "COMPLETE";
            return -1;
        } else {
            return 0;
        }
    }

    public void waitInReady() {
        PRIORITY = PRIORITY + 1;
    }

    public int waitInBlock(PriorityQueue readyQueue) {
        BLOCKTIME = BLOCKTIME - 1;


        //是否需要判断
//        if (readyQueue.size() == 0) {
//            STATE = "ready";
//            return 1;
//        }
        if (BLOCKTIME <= 0) {
            STATE = "READY";
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return
                "" + ID +
                        "\t\t" + PRIORITY +
                        "\t\t\t\t" + CPUTIME +
                        "\t\t\t" + ALLTIEM +
                        "\t\t\t" + STARTBLOCK +
                        "\t\t\t" + BLOCKTIME +
                        "\t\t" + STATE;
    }
}

/**
 * @author yz
 */
public class DynamicPriorityAlgorithm {

    public static void IteratorBlock(LinkedList<PCB> BlockQueue, PriorityQueue ReadyQueue) {
        System.out.print("BLOCK-QUEUE: ");

        for (int i = 0; i < BlockQueue.size(); i++) {
            PCB pcbBlock = BlockQueue.get(i);

            System.out.print("->ID" + pcbBlock.ID);

            if (pcbBlock.waitInBlock(ReadyQueue) == 1) {
                ReadyQueue.offer(pcbBlock);
                BlockQueue.remove(i);
            }
        }
        System.out.println();
    }

    public static void display(LinkedList<PCB> displayQueue) {

        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = == = =");
        System.out.println("ID\t\tPRIORITY\tCPUTIME\t\tALLTIME\t\tSTARTBLOCK\tBLOCKTIME\tSTATE");

        Iterator<PCB> displayQueueIte = displayQueue.iterator();

        while (displayQueueIte.hasNext()) {
            System.out.println(displayQueueIte.next().toString());
        }

        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
    }

    public static void main(String args[]) {


        Comparator<PCB> cmp = new Comparator<PCB>() {
            @Override
            public int compare(PCB o1, PCB o2) {
                return o2.PRIORITY - o1.PRIORITY;
            }
        };


        LinkedList<PCB> displayQueue = new LinkedList<>();

        PriorityQueue<PCB> readyQueue = new PriorityQueue(cmp);


        PCB pcb0 = new PCB(0, 9, 0, 3, 2, 3, "READY");
        PCB pcb1 = new PCB(1, 38, 0, 3, -1, 0, "READY");
        PCB pcb2 = new PCB(2, 30, 0, 6, -1, 0, "READY");
        PCB pcb3 = new PCB(3, 29, 0, 3, -1, 0, "READY");
        PCB pcb4 = new PCB(4, 0, 0, 4, -1, 0, "READY");

        readyQueue.add(pcb0);
        readyQueue.add(pcb1);
        readyQueue.add(pcb2);
        readyQueue.add(pcb3);
        readyQueue.add(pcb4);

        displayQueue.add(pcb0);
        displayQueue.add(pcb1);
        displayQueue.add(pcb2);
        displayQueue.add(pcb3);
        displayQueue.add(pcb4);

        LinkedList<PCB> blockQueue = new LinkedList<PCB>();

        int time = 0;
        while (blockQueue.size() + readyQueue.size() > 0) {
            time = time + 1;
            System.out.println("SLICE: " + time);
            if (readyQueue.size() > 0) {
                PCB pcbRunning = readyQueue.poll();
                if (pcbRunning != null) {
                    System.out.println("RUNNING PROG: " + pcbRunning.ID);
                    int state = pcbRunning.run();

                    if (readyQueue.size() > 0) {
                        Iterator<PCB> pcbIterator = readyQueue.iterator();
                        if (pcbIterator.hasNext()) {
                            System.out.print("READY-QUEUE: ");
                        }
                        try {
                            while (pcbIterator.hasNext()) {
                                PCB pcbWait = pcbIterator.next();
                                pcbWait.waitInReady();
                                System.out.print("->ID" + pcbWait.ID);
                            }
                            System.out.println();
                        } catch (Exception e) {

                        }


                    }
                    //最后一个进程


                    if (blockQueue.size() > 0) {
                        IteratorBlock(blockQueue, readyQueue);
                    }

                    if (state == 0) {
                        readyQueue.add(pcbRunning);
                    } else if (state == 1) {
                        blockQueue.add(pcbRunning);
                    }

                }


            } else if (blockQueue.size() > 0) {
                IteratorBlock(blockQueue, readyQueue);
            }

            display(displayQueue);
        }

        return;
    }

}
