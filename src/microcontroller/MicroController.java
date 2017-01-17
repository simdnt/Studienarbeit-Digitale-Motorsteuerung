package microcontroller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import connection.Wire;


public abstract class MicroController {

	private final String mSourceFileName;
	private Vector<String> mProgramm;
	private Thread mRunThread;

	protected boolean mIsClean = true;
	protected byte[] mRAM = new byte[65536];
	protected short mStackPointer, mDPTR;
	protected int mProgrammCounter;

	public MicroController(String aFileName){
		mSourceFileName=aFileName;
	}

	private Vector<String> readProgramm(String aFileName) {
		Vector<String> lProgramm = new Vector<String>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(aFileName)));
			String line;
			while((line=in.readLine())!=null){
				if(IsCommand(line))
					lProgramm.addElement(getCommand(line));
			}
			in.close();
			mIsClean=true;
		} catch (Exception e) {
			System.err.println("There is something wrong with the File: "+aFileName);
			mIsClean=false;
		}
		return lProgramm;
	}
	private String getCommand(String line) {
		return line.split(";")[0].trim().replace("\t", "").toLowerCase();
	}
	private boolean IsCommand(String line) {
		line = line.replace(" ", "").replace("\t", "");
		return !(line.startsWith(";")||line.length()==0);
	}

	public void reset(){
		turnOff();
		mProgramm = readProgramm(mSourceFileName);
		if(mIsClean){
			mStackPointer=256;
			mRunThread = new Thread(new Run(this));
			mRunThread.start();
		}
	}
	public void turnOff(){
		if(mRunThread!=null){
			mRunThread.interrupt();
			try{Thread.sleep(0);}catch(Exception e){}
		}
		mRAM=new byte[65536];
		mStackPointer=0;
		mDPTR=0;
		mProgrammCounter=0;
	}

	public static final class SenderController extends MicroController{
		private int mValueToSend=0;
		public SenderController(){
			super("Sender.asm");
		}
		public void setValueToSend(int aValue){
			mValueToSend=aValue;
			mRAM[10] = (byte)aValue;
		}
		@Override
		public void reset(){
			super.reset();
			mRAM[10] = (byte)mValueToSend;
		}
		@Override
		public void turnOff(){
			super.turnOff();
			Wire.setValue(false);
		}
	}
	public static final class ReceiverController extends MicroController{
		public ReceiverController(){
			super("Receiver.asm");
		}
		public int getReceivedValue(){
			return mRAM[10];
		}

	}

	private final class Run implements Runnable{

		private MicroController mMC;

		public Run(MicroController aMC){
			mMC = aMC;
		}

		@Override
		public void run() {
			try{

				while(true){
					if(mMC.mProgrammCounter>=mMC.mProgramm.size())
						return;
					execute(mMC.mProgramm.get(mMC.mProgrammCounter));
					Thread.sleep(2);
				}
				
			}catch(InterruptedException i){}
			catch(Exception e){e.printStackTrace();}
		}

		private void execute(String command) {
			String[] parts = command.split(" ");
			String[] args = null;
			if(parts.length>1)
				args = parts[1].split(",");
			if(parts[0].startsWith(":")){//Marke
				
			}else{
				switch(parts[0]){
					case "movx":
						mMC.mRAM[getValue(args[0])] = mMC.mRAM[getValue(args[1])];
						break;
					case "mov":
						mMC.mRAM[getValue(args[0])] = (byte)getValue(args[1]);
						break;
					case "add":
						mMC.mRAM[getValue(args[0])] = (byte)(mMC.mRAM[getValue(args[0])]+mMC.mRAM[getValue(args[1])]);
						break;
					case "sub":
						mMC.mRAM[getValue(args[0])] = (byte)(mMC.mRAM[getValue(args[0])]-mMC.mRAM[getValue(args[1])]);
						break;
					case "call":
						mMC.mRAM[mMC.mStackPointer] = (byte)(mMC.mProgrammCounter+1);
						mMC.mStackPointer++;
						mMC.mProgrammCounter = getValue(args[0]);
						break;
					case "ret":
						mMC.mStackPointer--;
						mMC.mProgrammCounter = mMC.mRAM[mMC.mStackPointer];
						return;
					case "push":
						mMC.mRAM[mMC.mStackPointer] = mMC.mRAM[getValue(args[0])];
						mMC.mStackPointer++;
						break;
					case "pop":
						mMC.mStackPointer--;
						mMC.mRAM[getValue(args[0])] = mMC.mRAM[mMC.mStackPointer];
						break;
					case "inc":
						mMC.mRAM[getValue(args[0])] = (byte)(mMC.mRAM[getValue(args[0])]+1);
						break;
					case "neg":
						break;
					case "nop":
						break;
					case "anl":
						break;
					case "orl":
						break;
					case "shl"://shift left
						break;
					case "shr"://shift right
						int zw = mMC.mRAM[getValue(args[0])];
						if(zw<0)
							zw+=255;
						mMC.mRAM[getValue(args[0])] = (byte)(zw/2);
						break;
					case "jmp":
						mMC.mProgrammCounter = getValue(args[0]);
						return;
					case "jeq":
						break;
					case "jne":
						if(mMC.mRAM[getValue(args[0])]!=0){
							mMC.mProgrammCounter = getValue(args[1]);
							return;
						}
						break;
					case "jlt":
						break;
					case "jgt":
						break;
					case "jle":
						break;
					case "jge":
						break;
					case "xby":
						int b = mMC.mRAM[getValue(args[0])];
						if(b<0)
							b=b+255;
						Wire.setValue(b%2==1);
						break;
						
					default:
						System.err.println("Command: "+parts[0]+" unknown");
						break;
				}
			}
			mMC.mProgrammCounter++;
		}

		private int getValue(String string) {
			if(string.startsWith("#")){
				if(string.endsWith("b")){//Binaer
					return Integer.valueOf(string.substring(1, string.length()-1), 2);
				}else if(string.endsWith("h")){//Hex
					return Integer.valueOf(string.substring(1, string.length()-1), 16);
				}else{//Dezimal
					return Integer.valueOf(string.substring(1, string.length()), 10);
				}
			}
			if(string.startsWith(":"))
				for (int i = 0; i < mMC.mProgramm.size(); i++)
					if(mMC.mProgramm.get(i).equals(string))
						return i;
			switch (string) {
			case "r0":return 0;
			case "r1":return 1;
			case "r2":return 2;
			case "r3":return 3;
			case "r4":return 4;
			case "r5":return 5;
			case "r6":return 6;
			case "r7":return 7;
			case "r8":return 8;
			case "r9":return 9;
			case "r10":return 10;
			case "r11":return 11;
			case "r12":return 12;
			case "r13":return 13;
			case "r14":return 14;
			case "r15":return 15;
			case "addat":return Wire.ReceiverValue()?(byte)1:(byte)0;
			default:
				break;
			}
			throw new RuntimeException("Could not resolve to int: "+string);
		}

	}
}
