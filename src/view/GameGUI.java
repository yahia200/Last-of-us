package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.characters.Character;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class GameGUI implements ActionListener, KeyListener {
	int h=-1;
	int anifps=1000;
	int bSize=42;
	
	boolean moved=false;
	boolean select1=true;
	
	public JFrame frame=new JFrame();
	public JFrame startFrame=new JFrame();
	
	public JPanel textPanel=new JPanel();
	public JPanel gamePanel=new JPanel();
	public JPanel bottomTextPanel=new JPanel();
	public JPanel controlPanel=new JPanel();
	
	public JTextArea heroInfo;
	public JTextArea goText;
	public JTextArea otherHeroInfo;
	public JTextArea targetInfo;
	public JTextArea exceptionInfo;
	public JTextArea trapHitInfo;
	public JTextArea selectMode;
	public JTextArea controls;
	
	JButton start;

	Hero selectedHero;
	Character selectedTarget;
	ArrayList<JButton> buttons= new ArrayList<JButton>();
	ImageIcon images[]=new ImageIcon[6];
	Game game=new Game();

	ImageIcon fIcon=new ImageIcon(((new ImageIcon("fighteranimation\\fighter.png").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_SMOOTH))));
	ImageIcon fighterMoveRightExitIcon=new ImageIcon(((new ImageIcon("fighteranimation\\fighteroutr.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon fighterMoveRightEnterIcon=new ImageIcon(((new ImageIcon("fighteranimation\\fighterinr.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon fighterMoveLeftExitIcon=new ImageIcon(((new ImageIcon("fighteranimation\\fighteroutl.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon fighterMoveLeftEnterIcon=new ImageIcon(((new ImageIcon("fighteranimation\\fighterinl.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon fighterMoveDownExitIcon=new ImageIcon(((new ImageIcon("fighteranimation\\fighteroutd.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon fighterMoveDownEnterIcon=new ImageIcon(((new ImageIcon("fighteranimation\\fighterind.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon fighterMoveUpExitIcon=new ImageIcon(((new ImageIcon("fighteranimation\\fighteroutu.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon fighterMoveUpEnterIcon=new ImageIcon(((new ImageIcon("fighteranimation\\fighterinu.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	
	ImageIcon mIcon=new ImageIcon(((new ImageIcon("medicanimation\\medic.png").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_SMOOTH))));
	ImageIcon medicMoveRightExitIcon=new ImageIcon(((new ImageIcon("medicanimation\\medicoutr.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon medicMoveRightEnterIcon=new ImageIcon(((new ImageIcon("medicanimation\\medicinr.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon medicMoveLeftExitIcon=new ImageIcon(((new ImageIcon("medicanimation\\medicoutl.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon medicMoveLeftEnterIcon=new ImageIcon(((new ImageIcon("medicanimation\\medicinl.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon medicMoveDownExitIcon=new ImageIcon(((new ImageIcon("medicanimation\\medicoutd.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon medicMoveDownEnterIcon=new ImageIcon(((new ImageIcon("medicanimation\\medicind.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon medicMoveUpExitIcon=new ImageIcon(((new ImageIcon("medicanimation\\medicoutu.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon medicMoveUpEnterIcon=new ImageIcon(((new ImageIcon("medicanimation\\medicinu.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	
	ImageIcon eIcon=new ImageIcon(((new ImageIcon("expanimation\\exp.png").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_SMOOTH))));
	ImageIcon expMoveRightExitIcon=new ImageIcon(((new ImageIcon("expanimation\\expoutr.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon expMoveRightEnterIcon=new ImageIcon(((new ImageIcon("expanimation\\expinr.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon expMoveLeftExitIcon=new ImageIcon(((new ImageIcon("expanimation\\expoutl.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon expMoveLeftEnterIcon=new ImageIcon(((new ImageIcon("expanimation\\expinl.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon expMoveDownExitIcon=new ImageIcon(((new ImageIcon("expanimation\\expoutd.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon expMoveDownEnterIcon=new ImageIcon(((new ImageIcon("expanimation\\expind.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon expMoveUpExitIcon=new ImageIcon(((new ImageIcon("expanimation\\expoutu.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));
	ImageIcon expMoveUpEnterIcon=new ImageIcon(((new ImageIcon("expanimation\\expinu.gif").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_DEFAULT))));

	
	ImageIcon bIcon=new ImageIcon(((new ImageIcon("black.png").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_SMOOTH))));
	ImageIcon wIcon=new ImageIcon(((new ImageIcon("white.png").getImage().getScaledInstance(3000, 3000,java.awt.Image.SCALE_SMOOTH))));
	ImageIcon zIcon=new ImageIcon(((new ImageIcon("zombie.png").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_SMOOTH))));
	ImageIcon sIcon=new ImageIcon(((new ImageIcon("supply 1.png").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_SMOOTH))));
	ImageIcon vIcon=new ImageIcon(((new ImageIcon("vaccine.png").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_SMOOTH))));
	ImageIcon gIcon=new ImageIcon(((new ImageIcon("greyd.png").getImage().getScaledInstance(bSize, bSize,java.awt.Image.SCALE_SMOOTH))));

	
	int bSizes=60;
	Color bgcolor=Color.black;
	Color fgcolor=Color.white;
	ArrayList<JButton> heroButtons=new ArrayList<JButton>();
	JPanel menu;
	JTextArea heroStartInfo;
	ArrayList<Hero> hlist;;

	Font pixelfont;
	public  void end(int r) {
		if(r==-1)
		goText.setText("GAME OVER!");
		if(r==1) {
			goText.setText("YOU WIN!");
			goText.setForeground(Color.GREEN);}
		for(JButton b: buttons) {
			b.setIcon(bIcon);
		}
		heroInfo.setText(null);
		otherHeroInfo.setText(null);
		exceptionInfo.setText(null);
		targetInfo.setText(null);
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	frame.removeAll();
		            }
		        }, 
		        100
		);
		
	}
	public void setControlPanel() {
		controls=new JTextArea("Attack: Q        Cure: W        EndTurn: E        UseSpeacial: R        SelectingMode: T        SelectHero: 1-6");
		controls.setBounds(0, 5, 630,18 );
		controls.setBackground(Color.black);
		controls.setForeground(Color.white);
		controls.setFont(new Font("Minecraft",Font.PLAIN,13));
		controls.addKeyListener(this);
		controls.setEditable(false);
		
		JLabel horzontalLine=new JLabel();
		horzontalLine.setIcon(wIcon);
		horzontalLine.setBackground(Color.white);
		horzontalLine.setBounds(0,28,752,3);
		
		controlPanel.setLayout(null);
		controlPanel.setBounds(0,0,752,31);
		controlPanel.setBackground(Color.black);
		controlPanel.setVisible(true);
		controlPanel.add(horzontalLine);
		controlPanel.add(controls);
	}
	public void setTextPanel() {
		JLabel verticalLine=new JLabel();
		verticalLine.setIcon(wIcon);
		verticalLine.setBackground(Color.white);
		verticalLine.setBounds(0,0,2,1000);
		
		
		JLabel horzontalLine=new JLabel();
		horzontalLine.setIcon(wIcon);
		horzontalLine.setBackground(Color.white);
		horzontalLine.setBounds(0,132,300,2);
		try{
            // load a custom font in your project folder
			pixelfont = Font.createFont(Font.TRUETYPE_FONT, new File("Minecraft.ttf")).deriveFont(30f);	
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Minecraft.ttf")));			
		}
		catch(IOException | FontFormatException e){
			
		}
		selectMode=new JTextArea("Selecting: Main hero");
		selectMode.setBounds(3, 760-120, 150,545-120 );
		selectMode.setBackground(Color.black);
		selectMode.setForeground(Color.white);
		selectMode.setFont(new Font("Minecraft",Font.PLAIN,12));
		selectMode.addKeyListener(this);
		selectMode.setEditable(false);
		
		
		otherHeroInfo=new JTextArea();
		otherHeroInfo.setBounds(3, 140, 150,545-120 );
		otherHeroInfo.setBackground(Color.black);
		otherHeroInfo.setForeground(Color.white);
		otherHeroInfo.setFont(new Font("Minecraft",Font.PLAIN,15));
		otherHeroInfo.addKeyListener(this);
		otherHeroInfo.setEditable(false);
		
		exceptionInfo=new JTextArea();
		exceptionInfo.setBounds(3, 710-120, 150, 50);
		exceptionInfo.setBackground(Color.black);
		exceptionInfo.setForeground(Color.red);
		exceptionInfo.setFont(new Font("Minecraft",Font.PLAIN,12));
		exceptionInfo.addKeyListener(this);
		exceptionInfo.setEditable(false);
		
		heroInfo=new JTextArea();
		heroInfo.setBounds(3, 5, 150, 122);
		heroInfo.setBackground(Color.black);
		heroInfo.setForeground(Color.white);
		heroInfo.setFont(new Font("Minecraft",Font.PLAIN,15));
		heroInfo.addKeyListener(this);
		heroInfo.setEditable(false);
		

		textPanel.setLayout(null);
		textPanel.setBounds(752-120,0,150,780);
		textPanel.setBackground(Color.black);
		textPanel.setVisible(true);
		textPanel.add(exceptionInfo);
		textPanel.add(heroInfo);
		textPanel.add(otherHeroInfo);
		textPanel.add(selectMode);
		textPanel.addKeyListener(this);
		textPanel.add(horzontalLine);
		textPanel.add(verticalLine);

	}
	public void setBottomTextPanel() {
		JLabel horzontalLine=new JLabel();
		horzontalLine.setIcon(wIcon);
		horzontalLine.setBackground(Color.white);
		horzontalLine.setBounds(0,0,900,2);
		bottomTextPanel.add(horzontalLine);
		
		targetInfo=new JTextArea();
		targetInfo.setBounds(3, 13, 250, 300);
		targetInfo.setBackground(Color.black);
		targetInfo.setForeground(Color.white);
		targetInfo.setFont(new Font("Minecraft",Font.PLAIN,15));
		targetInfo.addKeyListener(this);
		bottomTextPanel.add(targetInfo);
		targetInfo.setEditable(false);
		
		trapHitInfo=new JTextArea();
		trapHitInfo.setBounds(760-120, 13, 150, 150);
		trapHitInfo.setBackground(Color.black);
		trapHitInfo.setForeground(Color.red);
		trapHitInfo.setFont(new Font("Minecraft",Font.PLAIN,15));
		trapHitInfo.addKeyListener(this);
		trapHitInfo.setEditable(false);
		bottomTextPanel.add(trapHitInfo);
		
		goText=new JTextArea();
		goText.setBounds(380-120, 13, 150, 150);
		goText.setBackground(Color.black);
		goText.setForeground(Color.red);
		goText.setFont(new Font("Minecraft",Font.PLAIN,15));
		goText.addKeyListener(this);
		goText.setEditable(false);
		bottomTextPanel.add(goText);

		bottomTextPanel.setLayout(null);
		bottomTextPanel.setBounds(0,780-120,900-120,40);
		bottomTextPanel.setBackground(Color.black);
		bottomTextPanel.setVisible(true);
			
		
		
	}
	public void setGamePanel(int h) throws IOException {
		Game.loadHeroes("Heroes.csv");
		Game.startGame(Game.availableHeroes.get(h));
		gamePanel.setLayout(new GridLayout(15,15));
		gamePanel.setBounds(2,30,630,630);
		gamePanel.setBackground(Color.black);
		for(int i=0;i<225;i++) {
			JButton b=new JButton();
			b.setIcon(images[5]);
			b.addActionListener(this);
			b.addKeyListener(this);
			b.setBackground(Color.black);
			b.setBorder(null);
			buttons.add(b);;
			gamePanel.add(b);
		}
		updateMap();
		gamePanel.addKeyListener(this);
	}
	public void actionPerformed(ActionEvent e){
		checkMap(e);
		
		if(heroButtons.contains(e.getSource())) {
			int b=heroButtons.indexOf(e.getSource());
			heroInfo.setText(Game.availableHeroes.get(b).toStringOther());
			h=b;
		}
		if(e.getSource()==start&&h>=0) {
			startFrame.dispose();
			try {
				setframe(h);
			} catch (IOException e1) {
			}
		}
	}
	public void checkMap(ActionEvent e) {
		if(buttons.contains(e.getSource())) {
		int b=buttons.indexOf(e.getSource());
		int x=(int) getcor(b).getX();
		int y=(int) getcor(b).getY();
			if(Game.map[x][y] instanceof CharacterCell && ((CharacterCell) Game.map[x][y]).getCharacter() instanceof Hero) {
				if(select1) {
				selectedHero=(Hero) ((CharacterCell) Game.map[x][y]).getCharacter();
				heroInfo.setText(selectedHero.toString());
				}
				else if(!select1 && selectedHero!=null) {
					selectedTarget=((CharacterCell) Game.map[x][y]).getCharacter();
					targetInfo.setText(selectedTarget.toString());
					selectedHero.setTarget(selectedTarget);
					}
				updateOtherHero();}
			if(Game.map[x][y] instanceof CharacterCell && ((CharacterCell) Game.map[x][y]).getCharacter() instanceof Zombie) {
				selectedTarget=((CharacterCell) Game.map[x][y]).getCharacter();
				targetInfo.setText(selectedTarget.toString());
				if(selectedHero!=null)
				selectedHero.setTarget(selectedTarget);}
		}
		
	}
	public Point getcor(int b) {
		Point loc=new Point();
		for(int i=0;i<15;i++) {
			if((b-i*15>=0)&&((i+1)*15-b>0)) {
				loc.setLocation(14-i, b-(i)*15);
				break;
				}
		}
		return loc;
	}
	public int getInd(Point p) {
		int x =(int)p.getX();
		int y =(int)p.getY();
		return(15*(14-x))+y;
	}
	public void updateMap() {
		for(int x=0;x<15;x++) {
			for(int y=0;y<15;y++) {
				if(Game.map[x][y] instanceof TrapCell)
					buttons.get(getInd(new Point(x,y))).setIcon(bIcon);
				if(Game.map[x][y] instanceof CollectibleCell && ((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Vaccine)
					buttons.get(getInd(new Point(x,y))).setIcon(vIcon);
				if(Game.map[x][y] instanceof CollectibleCell && ((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Supply)
					buttons.get(getInd(new Point(x,y))).setIcon(sIcon);
				if(Game.map[x][y] instanceof CharacterCell && ((CharacterCell) Game.map[x][y]).getCharacter() instanceof Hero) {
					if(((CharacterCell) Game.map[x][y]).getCharacter() instanceof Fighter)
						buttons.get(getInd(new Point(x,y))).setIcon(fIcon);
					if(((CharacterCell) Game.map[x][y]).getCharacter() instanceof Medic)
						buttons.get(getInd(new Point(x,y))).setIcon(mIcon);
					if(((CharacterCell) Game.map[x][y]).getCharacter() instanceof Explorer)
						buttons.get(getInd(new Point(x,y))).setIcon(eIcon);
				}
					
				if(Game.map[x][y] instanceof CharacterCell && ((CharacterCell) Game.map[x][y]).getCharacter() instanceof Zombie)
					buttons.get(getInd(new Point(x,y))).setIcon(zIcon);
				if(Game.map[x][y] instanceof CharacterCell && ((CharacterCell) Game.map[x][y]).getCharacter() ==null)
					buttons.get(getInd(new Point(x,y))).setIcon(bIcon);
				if(!Game.map[x][y].isVisible())
					buttons.get(getInd(new Point(x,y))).setIcon(gIcon);
			}
		}
	}
	public void moveRight() throws MovementException, NotEnoughActionsException {
		if(selectedHero!=null) {
			int ind=getInd(selectedHero.getLocation());
			if(selectedHero instanceof Fighter) {
			buttons.get(ind).setIcon(fighterMoveRightExitIcon);
			buttons.get(ind+1).setIcon(fighterMoveRightEnterIcon);}
			if(selectedHero instanceof Medic) {
				buttons.get(ind).setIcon(medicMoveRightExitIcon);
				buttons.get(ind+1).setIcon(medicMoveRightEnterIcon);}
			if(selectedHero instanceof Explorer) {
				buttons.get(ind).setIcon(expMoveRightExitIcon);
				buttons.get(ind+1).setIcon(expMoveRightEnterIcon);}
			
			selectedHero.move(Direction.RIGHT);
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	fighterMoveRightExitIcon.getImage().flush();
			            	fighterMoveRightEnterIcon.getImage().flush();
			            	medicMoveRightEnterIcon.getImage().flush();
			            	medicMoveRightExitIcon.getImage().flush();
			            	expMoveRightExitIcon.getImage().flush();
			            	expMoveRightEnterIcon.getImage().flush();
			            	updateMap();

			            	exceptionInfo.setText("");
			            	
			            }
			        }, 
			        anifps 
			);
			
		}
		
	}	
	public void moveLeft() throws MovementException, NotEnoughActionsException {
		if(selectedHero!=null) {
			int ind=getInd(selectedHero.getLocation());
			if(selectedHero instanceof Fighter) {
			buttons.get(ind).setIcon(fighterMoveLeftExitIcon);
			buttons.get(ind-1).setIcon(fighterMoveLeftEnterIcon);}
			if(selectedHero instanceof Medic) {
				buttons.get(ind).setIcon(medicMoveLeftExitIcon);
				buttons.get(ind-1).setIcon(medicMoveLeftEnterIcon);}
			if(selectedHero instanceof Explorer) {
				buttons.get(ind).setIcon(expMoveLeftExitIcon);
				buttons.get(ind-1).setIcon(expMoveLeftEnterIcon);}

			selectedHero.move(Direction.LEFT);
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	fighterMoveLeftExitIcon.getImage().flush();
			            	expMoveLeftExitIcon.getImage().flush();
			            	medicMoveLeftExitIcon.getImage().flush();
			            	fighterMoveLeftEnterIcon.getImage().flush();
			            	medicMoveLeftEnterIcon.getImage().flush();
			            	expMoveLeftEnterIcon.getImage().flush();
			            	updateMap();

			            	exceptionInfo.setText("");
			            }
			        }, 
			        anifps 
			);
			
		}
		}
	public void moveUp() throws MovementException, NotEnoughActionsException {
		if(selectedHero!=null) {
			int ind=getInd(selectedHero.getLocation());
			if(selectedHero instanceof Fighter) {
			buttons.get(ind).setIcon(fighterMoveUpExitIcon);
			buttons.get(ind-15).setIcon(fighterMoveUpEnterIcon);}
			if(selectedHero instanceof Medic) {
				buttons.get(ind).setIcon(medicMoveUpExitIcon);
				buttons.get(ind-15).setIcon(medicMoveUpEnterIcon);}
			if(selectedHero instanceof Explorer) {
				buttons.get(ind).setIcon(expMoveUpExitIcon);
				buttons.get(ind-15).setIcon(expMoveUpEnterIcon);}

			selectedHero.move(Direction.UP);
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	fighterMoveUpExitIcon.getImage().flush();
			            	expMoveUpExitIcon.getImage().flush();
			            	medicMoveUpExitIcon.getImage().flush();
			            	fighterMoveUpEnterIcon.getImage().flush();
			            	medicMoveUpEnterIcon.getImage().flush();
			            	expMoveUpEnterIcon.getImage().flush();
			            	updateMap();

			            	exceptionInfo.setText("");
			            }
			        }, 
			        anifps 
			);
			
		}
			}
	public void moveDown() throws MovementException, NotEnoughActionsException {
		if(selectedHero!=null) {
			int ind=getInd(selectedHero.getLocation());
			if(selectedHero instanceof Fighter) {
			buttons.get(ind).setIcon(fighterMoveDownExitIcon);
			buttons.get(ind+15).setIcon(fighterMoveDownEnterIcon);}
			if(selectedHero instanceof Medic) {
				buttons.get(ind).setIcon(medicMoveDownExitIcon);
				buttons.get(ind+15).setIcon(medicMoveDownEnterIcon);}
			if(selectedHero instanceof Explorer) {
				buttons.get(ind).setIcon(expMoveDownExitIcon);
				buttons.get(ind+15).setIcon(expMoveDownEnterIcon);}

			selectedHero.move(Direction.DOWN);
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	fighterMoveDownExitIcon.getImage().flush();
			            	expMoveDownExitIcon.getImage().flush();
			            	medicMoveDownExitIcon.getImage().flush();
			            	fighterMoveDownEnterIcon.getImage().flush();
			            	medicMoveDownEnterIcon.getImage().flush();
			            	expMoveDownEnterIcon.getImage().flush();
			            	updateMap();

			            	exceptionInfo.setText("");
			            }
			        }, 
			        anifps 
			);
			
		}
				}
	public void attack() throws NotEnoughActionsException, InvalidTargetException {
					if(selectedHero!=null) {
						if(selectedTarget!=null) {
							selectedHero.setTarget(selectedTarget);
							selectedHero.attack();
							heroInfo.setText(selectedHero.toString());
							targetInfo.setText(selectedTarget.toString());
							new java.util.Timer().schedule( 
							        new java.util.TimerTask() {
							            @Override
							            public void run() {
							            	fighterMoveRightExitIcon.getImage().flush();
							            	fighterMoveRightEnterIcon.getImage().flush();
							            	updateMap();
							            	updateOtherHero();
				
							            	exceptionInfo.setText("");
							            }
							        }, 
							        anifps 
							);
						}
					}
				}
	public void cure() throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException {
					if(selectedHero!=null) {
						if(selectedTarget!=null) {
							selectedHero.setTarget(selectedTarget);
							selectedHero.cure();
							new java.util.Timer().schedule( 
							        new java.util.TimerTask() {
							            @Override
							            public void run() {
							            	fighterMoveRightExitIcon.getImage().flush();
							            	fighterMoveRightEnterIcon.getImage().flush();
							            	updateMap();
							            	updateOtherHero();
				
							            	exceptionInfo.setText("");
							            }
							        }, 
							        anifps 
							);
						}
					}
				}
	public void keyTyped(KeyEvent e) {
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if(selectedHero!=null) {
		checkMove(k);
		checkCure(k);
		checkAttack(k);
		checkEndTurn(k);
		checkSpecial(k);}
		checkGameOver();
		selecthero(k);
		if(k==KeyEvent.VK_T) {
			select1=!select1;
			if(select1) 
				selectMode.setText("Selecting: Main hero");
			else
				selectMode.setText("Selecting: Target");
			
		}
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	updateMap();

		            	exceptionInfo.setText("");
		            }
		        }, 
		        anifps
		);
			
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
	public void checkCure(int k) {
		if(k==KeyEvent.VK_W)
			try {
				cure();
				Game.checkWin();
				if(selectedHero!=null)
				heroInfo.setText(selectedHero.toString());
				if(selectedTarget!=null)
				targetInfo.setText(selectedTarget.toString());
			} catch (NoAvailableResourcesException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			} catch (InvalidTargetException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			} catch (NotEnoughActionsException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			}
	}				
	public void checkAttack(int k) {
		if(k==KeyEvent.VK_Q)
			try {
				attack();
				if(selectedHero!=null)
				heroInfo.setText(selectedHero.toString());
			} catch (NotEnoughActionsException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			} catch (InvalidTargetException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			}
	}
	public void checkMove(int k) {
		int b=selectedHero.getCurrentHp();;
		if(!moved) {
		if(k==KeyEvent.VK_RIGHT)
			try {
				moveRight();
            	chechTrapCell(b,selectedHero.getCurrentHp());
				moved=true;
				if(selectedHero!=null)
				heroInfo.setText(selectedHero.toString());
			} catch (MovementException e1) {
				exceptionInfo.setText(e1.getMessage());
				int x= (int)selectedHero.getLocation().getX();
				int y= (int)selectedHero.getLocation().getY();
				if(y<14) {
					if(Game.map[x][y+1] instanceof CharacterCell && ((CharacterCell) Game.map[x][y+1]).getCharacter() instanceof Zombie)
						selectedTarget=(Zombie) ((CharacterCell) Game.map[x][y+1]).getCharacter();
						targetInfo.setText(selectedTarget.toString());
				}
				updateMap();
			} catch (NotEnoughActionsException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			}
		if(k==KeyEvent.VK_LEFT)
			try {
				moveLeft();
            	chechTrapCell(b,selectedHero.getCurrentHp());
				if(selectedHero!=null)
				heroInfo.setText(selectedHero.toString());
				moved=true;
			} catch (MovementException e1) {
				exceptionInfo.setText(e1.getMessage());
				int x= (int)selectedHero.getLocation().getX();
				int y= (int)selectedHero.getLocation().getY();
				if(y>1) {
					if(Game.map[x][y-1] instanceof CharacterCell && ((CharacterCell) Game.map[x][y-1]).getCharacter() instanceof Zombie)
						selectedTarget=(Zombie) ((CharacterCell) Game.map[x][y-1]).getCharacter();
						targetInfo.setText(selectedTarget.toString());
				}
				updateMap();
			} catch (NotEnoughActionsException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			}
		if(k==KeyEvent.VK_UP)
			try {
				moveUp();
            	chechTrapCell(b,selectedHero.getCurrentHp());
				if(selectedHero!=null)
				heroInfo.setText(selectedHero.toString());
				moved=true;
			} catch (MovementException e1) {
				exceptionInfo.setText(e1.getMessage());
				int x= (int)selectedHero.getLocation().getX();
				int y= (int)selectedHero.getLocation().getY();
				if(x<14) {
					if(Game.map[x+1][y] instanceof CharacterCell && ((CharacterCell) Game.map[x+1][y]).getCharacter() instanceof Zombie)
						selectedTarget=(Zombie) ((CharacterCell) Game.map[x+1][y]).getCharacter();
						targetInfo.setText(selectedTarget.toString());
				}
				updateMap();
			} catch (NotEnoughActionsException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			}
		if(k==KeyEvent.VK_DOWN)
			try {
				moveDown();
            	chechTrapCell(b,selectedHero.getCurrentHp());
				if(selectedHero!=null)
				heroInfo.setText(selectedHero.toString());
				moved=true;
			} catch (MovementException e1) {
				exceptionInfo.setText(e1.getMessage());
				int x= (int)selectedHero.getLocation().getX();
				int y= (int)selectedHero.getLocation().getY();
				if(x>1) {
					if(Game.map[x-1][y] instanceof CharacterCell && ((CharacterCell) Game.map[x-1][y]).getCharacter() instanceof Zombie)
						selectedTarget=((CharacterCell) Game.map[x-1][y]).getCharacter();
						targetInfo.setText(selectedTarget.toString());
				}
				updateMap();
			} catch (NotEnoughActionsException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			}
		}
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
			            	didmove();
		            }
		        }, 
		        anifps 
		);
		
	}
	public void didmove() {
		moved = false;
		trapHitInfo.setText("");	
	}
	public void checkEndTurn(int k) {
		if(k==KeyEvent.VK_E)
			try {
				Game.endTurn();
				updateMap();
				if(selectedHero!=null)
				heroInfo.setText(selectedHero.toString());
				if(selectedTarget!=null)
				targetInfo.setText(selectedTarget.toString());
			} catch (NotEnoughActionsException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			} catch (InvalidTargetException e1) {
				exceptionInfo.setText(e1.getMessage());
				updateMap();
			}
	}
	public void selecthero(int k) {
		if(k==KeyEvent.VK_1 && Game.heroes.size()>=1) {
			selectedHero=Game.heroes.get(0);
			heroInfo.setText(selectedHero.toString());
		}
		if(k==KeyEvent.VK_2 && Game.heroes.size()>=2) {
			selectedHero=Game.heroes.get(1);
			heroInfo.setText(selectedHero.toString());
		}
		if(k==KeyEvent.VK_3 && Game.heroes.size()>=3) {
			selectedHero=Game.heroes.get(2);
			heroInfo.setText(selectedHero.toString());
		}
		if(k==KeyEvent.VK_4 && Game.heroes.size()>=4) {
			selectedHero=Game.heroes.get(3);
			heroInfo.setText(selectedHero.toString());
		}
		if(k==KeyEvent.VK_5 && Game.heroes.size()>=5) {
			selectedHero=Game.heroes.get(4);
			heroInfo.setText(selectedHero.toString());
		}
		if(k==KeyEvent.VK_6 && Game.heroes.size()>=6) {
			selectedHero=Game.heroes.get(5);
			heroInfo.setText(selectedHero.toString());
		}
	}
	public void checkSpecial(int k) {
		if(k==KeyEvent.VK_R) {
			if(selectedHero!=null) {
				try {
					selectedHero.useSpecial();
				} catch (NoAvailableResourcesException e) {
					exceptionInfo.setText(e.getMessage());
				} catch (InvalidTargetException e) {
					exceptionInfo.setText(e.getMessage());
				}
				heroInfo.setText(selectedHero.toString());
				if(selectedTarget!=null)
				targetInfo.setText(selectedTarget.toString());
				updateMap();
				updateOtherHero();
			}
		}
	}	
	public void updateOtherHero() {
		String otherH="";
		for(Hero h : Game.heroes) {
			if(!h.equals(selectedHero))
			otherH +=h.toStringOther()+ "\n"+"\n";
			}
		
		otherHeroInfo.setText(otherH);
		
		}
	public void chechTrapCell(int b,int a) {
		if(b>a) {
			trapHitInfo.setText("You hit a trap");
			
			}
	}
	public void checkGameOver() {
		if(Game.checkGameOver()) {
			end(-1);
		}
		if(Game.checkWin()) {
			end(1);
		}
	}	
	public void setStart() throws FontFormatException, IOException {
		pixelfont = Font.createFont(Font.TRUETYPE_FONT, new File("Minecraft.ttf")).deriveFont(30f);	
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Minecraft.ttf")));	
		
		JTextArea gameInfo=new JTextArea();
		gameInfo.setBounds(590,530, 450, 500);
		gameInfo.setBackground(bgcolor);
		gameInfo.setForeground(fgcolor);
		gameInfo.setText("                How to play "+"\n"+"\n"+"       At the top is your controls"+"\n"+"\n"+"  right side is all your heroes info"+"\n"+"\n"+"the objective is to use all 5 vaccines "+"\n"+"\n"+" and cure 5 zombies while not dying");
		gameInfo.setFont(new Font("Minecraft",Font.PLAIN,15));
		
		heroInfo=new JTextArea();
		heroInfo.setBounds(5,560, 200, 500);
		heroInfo.setBackground(bgcolor);
		heroInfo.setForeground(fgcolor);
		heroInfo.setFont(new Font("Minecraft",Font.PLAIN,20));
		
		JTextArea selectText=new JTextArea();
		selectText.setText("SELECT YOUR STARTING HERO");
		selectText.setBounds(220, 10, 455, 100);
		selectText.setBackground(bgcolor);
		selectText.setForeground(fgcolor);
		selectText.setFont(new Font("Minecraft",Font.PLAIN,28));
		
		
		menu=new JPanel();
		menu.setBackground(bgcolor);
		menu.add(selectText);
		menu.setLayout(null);
		menu.add(heroInfo);
		menu.add(gameInfo);
		loadheroes(Game.availableHeroes);
		
		
		
		startFrame.setSize(900,730);
		startFrame.setVisible(true);
		startFrame.add(menu);
		startFrame.setLocationRelativeTo(null);
		
		start=new JButton("START");
		start.setBackground(bgcolor);
		start.setForeground(fgcolor);
		start.setBounds(310,450,200,100);
		start.setFont(new Font("Minecraft",Font.PLAIN,28));
		start.addActionListener(this);
		
		menu.add(start);
	}
	public void loadheroes(ArrayList<Hero> hlist) {
		int x=10;
		for(int i=0;i<hlist.size();i++) {
			JButton hb=new JButton();
			hb.setBorder(BorderFactory.createEtchedBorder());
			if(hlist.get(i) instanceof Fighter)
				hb.setIcon(fIcon);
			if(hlist.get(i) instanceof Explorer)
				hb.setIcon(eIcon);
			if(hlist.get(i) instanceof Medic)
				hb.setIcon(mIcon);
			hb.setBounds(x, 150, bSize, bSize);
			x+= 115;
			hb.addActionListener(this);
			heroButtons.add(hb);
			hb.setBackground(Color.DARK_GRAY);
			menu.add(hb);
		}
	}
public void setframe(int h) throws IOException {
		setTextPanel();
		setGamePanel(h);
		setBottomTextPanel();
		setControlPanel();
		frame=new JFrame();
		frame.setBackground(Color.white);
		frame.setLayout(null);
		frame.add(bottomTextPanel);
		frame.add(gamePanel);
		frame.add(textPanel);
		frame.add(controlPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(782,730);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
	public static void main(String[] args) throws Exception{
		GameGUI game=new GameGUI();
		Game.loadHeroes("Heroes.csv");
		game.setStart();
		
	}
	

	
	
	
}
