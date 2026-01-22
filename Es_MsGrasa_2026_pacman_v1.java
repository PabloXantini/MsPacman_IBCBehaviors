package MsGrasa2026;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class Es_MsGrasa_2026_pacman_v1 extends PacmanController{
	private EnumMap<GHOST, Ghost> ghostsInfo = new EnumMap<GHOST, Ghost>(GHOST.class);
	private Color pacmanColor = new Color(255,222,33);
	//MOD FLAGS
	private boolean DEBUG = false;
	//FSM METHODS
	private PacmanState currentState = new Greedy(this); //Always starts with greedy mode
	public Es_MsGrasa_2026_pacman_v1() {
	}
	public boolean getDebug() {
		return DEBUG;
	}
	@Override
	public MOVE getMove(Game game, long timeDue) {
		return handle(game);
	}
	public MOVE handle(Game game) {
		currentState.check(game);
		MOVE nextMove = currentState.execute(game);
		return nextMove;
	}
	public void changeState(PacmanState state) {
		currentState = state;
		if(DEBUG) currentState.notifyState();
	}
	//REQUEST GAME USEFUL HINT METHODS
	//PACMAN
	public Color getColor() {
		return pacmanColor;
	}
	public int getPosition(Game game) {
		return game.getPacmanCurrentNodeIndex();
	}
	public MOVE getLastMove(Game game) {
		return game.getPacmanLastMoveMade();
	}
	//GHOSTS
	public EnumMap<GHOST, Ghost> getGhostsInfo(){
		return ghostsInfo;
	}
	public boolean isGhostFree(Game game, GHOST ghost) {
		return game.getGhostLairTime(ghost) <= 0;
	}
	public boolean isGhostGoingToFree(Game game, GHOST ghost) {
		int lairTime = game.getGhostLairTime(ghost);
		return lairTime > 0 && lairTime < 30;
	}
	public boolean isGhostVulnerable(Game game, GHOST ghost) {
		return game.getGhostEdibleTime(ghost) > 30;
	}
	public void colorGhostPath(Game game, GHOST ghost, int... path) {
		switch(ghost) {
			case BLINKY:
				GameView.addPoints(game, Color.RED, path);
				break;
			case INKY:
				GameView.addPoints(game, Color.CYAN, path);
				break;
			case PINKY:
				GameView.addPoints(game, Color.PINK, path);
				break;
			case SUE:
				GameView.addPoints(game, Color.ORANGE, path);
				break;
			default:
				break;
		}
	}
	public Color getGhostColor(GHOST ghost) {
		switch (ghost) {
		case BLINKY: return Color.RED;
		case INKY: return Color.CYAN;
		case PINKY: return Color.PINK;
		case SUE: return Color.ORANGE;
		default: return Color.BLACK;
		}
	}
	public Ghost getNearestGhost(Game game, int position, DM Distance) {
		Ghost NearestGhost = null;
		double NearestGhostDistance = Double.MAX_VALUE;
		for (GHOST ghost : GHOST.values()) {
			boolean free = isGhostFree(game, ghost);
			boolean edible = game.isGhostEdible(ghost);
			int GhostPosition = game.getGhostCurrentNodeIndex(ghost);
			double GhostDistance = game.getDistance(position, GhostPosition, Distance);
			Ghost info = new Ghost(ghost, GhostPosition);
			info.setDistance(GhostDistance, Distance);
			ghostsInfo.put(ghost, info);
			if(!edible && free && GhostDistance < NearestGhostDistance) {
				NearestGhostDistance = GhostDistance;
				NearestGhost = info;
			}
			if(DEBUG && free) {
				GameView.addLines(game, Color.MAGENTA, position, GhostPosition);
			}
		}
		if(DEBUG && NearestGhost!=null) {
			GameView.addLines(game, Color.WHITE, position, NearestGhost.getPosition());
		}
		return NearestGhost;
	}
	public Ghost getNearestGhostToPursue(Game game, int position, MOVE lastMove) {
		Ghost NearestGhost = null;
		double NearestGhostDistance = Double.MAX_VALUE;
		for (GHOST ghost : GHOST.values()) {
			boolean free = isGhostFree(game, ghost);
			boolean vulnerable = isGhostVulnerable(game, ghost);
			int GhostPosition = game.getGhostCurrentNodeIndex(ghost);
			if(vulnerable && free) {
				double GhostDistance = game.getShortestPathDistance(position, GhostPosition, lastMove);
				Ghost info = new Ghost(ghost, GhostPosition);
				info.setDistance(GhostDistance, DM.PATH);
				ghostsInfo.put(ghost, info);
				if (GhostDistance < NearestGhostDistance) {
					NearestGhostDistance = GhostDistance;
					NearestGhost = info;					
				}
			}
			if(DEBUG && free && vulnerable) {
				GameView.addLines(game, Color.MAGENTA, position, GhostPosition);
			}
		}
		if(DEBUG && NearestGhost!=null) {
			GameView.addLines(game, Color.WHITE, position, NearestGhost.getPosition());
		}
		
		return NearestGhost;
	}
	//PILLS
	public Pill getNearestPowerPill(Game game, int position, int[] ActivePowerPills, DM Distance) {
		Pill NearestPowerPill;
		int PPillIndex = 0;
		double PPillDistance = Double.MAX_VALUE;
		for (int pos : ActivePowerPills) {
			double PowerPillDistance = game.getDistance(position, pos, Distance);
			if(PowerPillDistance < PPillDistance) {
				PPillDistance = PowerPillDistance;
				PPillIndex = pos;
			}
			if(DEBUG) {
				GameView.addLines(game, Color.RED, position, PPillIndex);
			}
		}
		NearestPowerPill = new Pill(PPillIndex);
		NearestPowerPill.setDistance(PPillDistance, Distance);
		if(DEBUG) {
			GameView.addLines(game, Color.WHITE, position, PPillIndex);
		}
		return NearestPowerPill; 
	}
	public int[] computePathFreedomDegrees(Game game, int[] path) {
		int[] weigthedPath = new int[path.length];
		for(int i = 0; i<path.length; i++) {
			int[] inodes = game.getNeighbouringNodes(path[i]);
			weigthedPath[i] = inodes.length - 1; 
		}
		return weigthedPath;
	}
	/*
	public double computeGhostMenace(Game game, int[] path, GHOST ghost, int PacmanPosition) {
		double risk = 0.0d;
		int lairBonus = 0;
		int sumWeights = 0;
		double mediaWeight = 0;
		if(path.length != 0) {
			int[] weightedPath = computePathFreedomDegrees(game, path);
			for (int i : weightedPath) {
				sumWeights += i;
			}
			mediaWeight = (double) sumWeights / weightedPath.length;
		}
		if(nearAlmostFree(game, ghost, PacmanPosition)) lairBonus = 2;
		risk = lairBonus + mediaWeight;
		return risk;
	}*/
	public boolean thereAlternatives(int[] wpath) {
		for (int i : wpath) {
			if(i >= 2) {
				return true;
			}
		}
		return false;
	}
	public PathNode getNextAlternative(Game game, int position, MOVE lastMove, Color color) {
		MOVE moveMark = lastMove;
		int positionMark = position;
		while (!game.isJunction(positionMark)) {
			if(DEBUG) GameView.addPoints(game, color, positionMark);
			int nextMark = game.getNeighbouringNodes(positionMark, moveMark)[0];
			moveMark = game.getMoveToMakeToReachDirectNeighbour(positionMark, nextMark);
			positionMark = nextMark;
		}
		return new PathNode(positionMark, moveMark);
	}
	public PathNode getPathTree(Game game, int position, MOVE lastMove, int depth, Color color) {
		PathNode node = new PathNode(position, lastMove);
		if(depth==0) return node;
		int[] neighbours = game.getNeighbouringNodes(position, lastMove);
		MOVE[] moves = game.getPossibleMoves(position, lastMove);
		//if(neighbours.length < 2) return node;
		PathNode[] children = new PathNode[neighbours.length];
		for (int i = 0; i < neighbours.length; i++) {
			PathNode next = getNextAlternative(game, neighbours[i], moves[i], color);
			children[i] = getPathTree(game, next.getOut(), next.getLastMove(), depth -1, color);
		}
		node.addPaths(children);
		return node;
	}
}

abstract class PacmanState {
	private Es_MsGrasa_2026_pacman_v1 pacman;
	public PacmanState(Es_MsGrasa_2026_pacman_v1 pacman) {
		this.pacman = pacman;
	}
	public Es_MsGrasa_2026_pacman_v1 getPacman() {
		return pacman;
	}
	public abstract void check(Game game);
	public abstract MOVE execute(Game game);
	public abstract void notifyState();
}

abstract class PacmanRule {
	private Es_MsGrasa_2026_pacman_v1 pacman;
	public PacmanRule(Es_MsGrasa_2026_pacman_v1 pacman) {
		this.pacman = pacman;
	}
	public Es_MsGrasa_2026_pacman_v1 getPacman() {
		return pacman;
	}
	public abstract boolean evaluate(Game game);
}

abstract class PacmanAction {
	private Es_MsGrasa_2026_pacman_v1 pacman;
	public PacmanAction(Es_MsGrasa_2026_pacman_v1 pacman) {
		this.pacman = pacman;
	}
	public Es_MsGrasa_2026_pacman_v1 getPacman() {
		return pacman;
	}
	public abstract MOVE apply(Game game);
}

class Location {
	private int position;
	private double euclidianDistance;
	private double manhattanDistance;
	private double pathDistance;
	public Location(int position) {
		this.position = position;
	}
	public int getPosition() {
		return position;
	}
	public double getDistance(DM metric) {
		switch (metric) {
		case EUCLID: return euclidianDistance;
		case MANHATTAN: return manhattanDistance;
		case PATH: return pathDistance;
		default: return 0.0d;
		}
	}
	public void setDistance(double distance, DM metric) {
		switch (metric) {
		case EUCLID:
			this.euclidianDistance = distance;
			break;
		case MANHATTAN:
			this.manhattanDistance = distance;
			break;
		case PATH:
			this.pathDistance = distance;
			break;
		default:
			break;
		}
	}
}

class Pill extends Location{
	public Pill(int position) {
		super(position);
	}
}

class Ghost extends Location {
	private double risk;
	private GHOST name;
	public Ghost(GHOST name, int position) {
		super(position);
		this.name = name;
	}
	public GHOST getName() {
		return name;
	}
	public double getRisk() {
		return risk;
	}
	public void setRisk(double risk) {
		this.risk = risk;
	}
}

abstract class PathVisitor {
	public PathVisitor() {
	}
	public abstract void visit(Game game, PathNode node);
}

class OutComparator extends PathVisitor {
	private Set<Integer> outs = new HashSet<Integer>();
	public OutComparator() {
	}
	public Set<Integer> getOuts(){
		return outs;
	}
	@Override
	public void visit(Game game, PathNode node) {
		outs.add(node.getOut());
		if(node.getPaths()==null) return;
		for (PathNode path : node.getPaths()) {
			path.accept(game, this);
		}
	}
}

class PathNode {
	private MOVE lastMove;
	private int nodeOut;
	private double score;
	private PathNode[] paths;
	public PathNode(int nodeOut, MOVE lastMove) {
		this.nodeOut = nodeOut;
		this.lastMove = lastMove;
	}
	public MOVE getLastMove() {
		return lastMove;
	}
	public int getOut() {
		return nodeOut;
	}
	public double getScore() {
		return score;
	}
	public PathNode[] getPaths() {
		return paths;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public void addPaths(PathNode[] paths) {
		this.paths = paths;
	}
	public void accept(Game game, PathVisitor visitor) {
		visitor.visit(game, this);
	}
}
//RULES
//SR: State Rules -> For state changes
class Powered extends PacmanRule {
	public Powered(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public boolean evaluate(Game game) {
		return game.wasPowerPillEaten();
	}
}

class NoVulnerableGhosts extends PacmanRule {
	public NoVulnerableGhosts(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public boolean evaluate(Game game) {
		int vulnerableGhostCount = 0;
		for (GHOST ghost : GHOST.values()) {
			if(getPacman().isGhostVulnerable(game, ghost)) vulnerableGhostCount++;
		}
		return vulnerableGhostCount == 0;
	}
}
class Eaten extends PacmanRule {
	public Eaten(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public boolean evaluate(Game game) {
		return game.wasPacManEaten();
	}
}
class NearToGhosts extends PacmanRule {
	private static final int NEAR_DISTANCE = 50;
	public NearToGhosts(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public boolean evaluate(Game game) {
		int position = getPacman().getPosition(game);
		Ghost nGhost = getPacman().getNearestGhost(game, position, DM.EUCLID);
		if(nGhost==null) return false;
		if(nGhost.getDistance(DM.EUCLID) < NEAR_DISTANCE) {
			return true;
		}
		return false;
	}
}
//AR: Actions Rules -> For move choices in state
class Hunger extends PacmanRule {
	private int hungryMeter = 0;
	private static final int HUNGRY_LEVEL = 40;
	public Hunger(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public boolean evaluate(Game game) {
		if(!game.wasPillEaten()) {
			hungryMeter++;
		} else {
			hungryMeter--;
		}
		if(hungryMeter > HUNGRY_LEVEL) {
			if(getPacman().getDebug()) System.out.println("MsPacman is hungry");
			hungryMeter-=10;
			return true;
		}
		return false;
	}
	
}

class NearToPowerPill extends PacmanRule {
	private static final int NEAR_DISTANCE = 40;
	public NearToPowerPill(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public boolean evaluate(Game game) {
		int position = getPacman().getPosition(game);
		int[] powerPills = game.getActivePowerPillsIndices();
		Pill nPPill = getPacman().getNearestPowerPill(game, position, powerPills, DM.EUCLID);
		if(nPPill.getDistance(DM.EUCLID) < NEAR_DISTANCE) {
			int[] path = game.getShortestPath(position, nPPill.getPosition());
			int[] wpath = getPacman().computePathFreedomDegrees(game, path);
			if(!getPacman().thereAlternatives(wpath)) {
				return true;
			}
		}
		return false;
	}
}

class NearToGhostLairHazard extends PacmanRule {
	private static final double NEAR_DISTANCE = 20.0d;
	public NearToGhostLairHazard(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public boolean evaluate(Game game) {
		double distance = 0.0d;
		int position = getPacman().getPosition(game);
		for(GHOST ghost : GHOST.values()) {
			if(getPacman().isGhostGoingToFree(game, ghost)) {
				int gPosition = game.getGhostCurrentNodeIndex(ghost);
				distance = game.getDistance(position, gPosition, DM.EUCLID);
				break;
			}
		}
		if(distance < NEAR_DISTANCE) return true;
		return false;
	}	
} 

//ACTIONS - Actions than always do MsPacman in a state
class AvoidPowerPill extends PacmanAction {
	public AvoidPowerPill(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public MOVE apply(Game game) {
		int position = getPacman().getPosition(game);
		MOVE lastMove = getPacman().getLastMove(game);
		int[] powerPills = game.getActivePowerPillsIndices();
		Pill nPPill = getPacman().getNearestPowerPill(game, position, powerPills, DM.EUCLID);
		if(getPacman().getDebug()) System.out.println("MsPacman avoiding PowerPill");
		return game.getNextMoveAwayFromTarget(position, nPPill.getPosition(), lastMove, DM.EUCLID);
	}
}

class GoToPowerPill extends PacmanAction {
	public GoToPowerPill(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public MOVE apply(Game game) {
		int position = getPacman().getPosition(game);
		MOVE lastMove = getPacman().getLastMove(game);
		int[] powerPills = game.getActivePowerPillsIndices();
		Pill nPPill = getPacman().getNearestPowerPill(game, position, powerPills, DM.EUCLID);
		if(getPacman().getDebug()) System.out.println("MsPacman going to PowerPill");
		return game.getNextMoveTowardsTarget(position, nPPill.getPosition(), lastMove, DM.PATH);
	}
}

class RunAway extends PacmanAction {
	private static final double DANGER_NEAR_DISTANCE = 30.0d;
	private static final double SAFE_NEAR_DISTANCE = 50.0d;
	private OutComparator co = new OutComparator();
	private PacmanRule r1 = new NearToPowerPill(getPacman());
	private PacmanAction a1 = new AvoidPowerPill(getPacman());
	private PacmanAction a2 = new RandomMove(getPacman());
	private PacmanAction a3 = new GoToPowerPill(getPacman());
	public RunAway(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public MOVE apply(Game game) {
		int position = getPacman().getPosition(game);
		MOVE lastMove = getPacman().getLastMove(game);
		Ghost nGhost = getPacman().getNearestGhost(game, position, DM.PATH);
		PathNode pacTree = getPacman().getPathTree(game, position, lastMove, 1, getPacman().getColor());
		co.getOuts().clear();
		GHOST[] ghosts = GHOST.values();
		for (GHOST ghost : ghosts) {
			int gPosition = game.getGhostCurrentNodeIndex(ghost);
			MOVE gMove = game.getGhostLastMoveMade(ghost);
			PathNode gtree = getPacman().getPathTree(game, gPosition, gMove, 2, getPacman().getGhostColor(ghost));
			gtree.accept(game, co);
		}
		if(nGhost==null) return a2.apply(game);
		if(getPacman().getDebug()) System.out.println("MsPacman running away from a ghost");
		if(nGhost.getDistance(DM.PATH) > SAFE_NEAR_DISTANCE && !r1.evaluate(game)) {
			return a3.apply(game);
		}
		if(nGhost.getDistance(DM.PATH) > SAFE_NEAR_DISTANCE && r1.evaluate(game)) {
			return a1.apply(game);
		}
		if(nGhost.getDistance(DM.PATH) > DANGER_NEAR_DISTANCE) {
			return game.getNextMoveAwayFromTarget(position, nGhost.getPosition(), DM.EUCLID);
		}
		for(PathNode path : pacTree.getPaths()) {
			if(!co.getOuts().contains(path.getOut())) {
				return path.getLastMove();
			}
		}
		return game.getPacmanLastMoveMade();
	}
}

class ChaseGhost extends PacmanAction {
	private Ghost GhostTarget = null;
	public ChaseGhost(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public MOVE apply(Game game) {
		int position = getPacman().getPosition(game);
		MOVE lastMove = getPacman().getLastMove(game);
		Ghost nGhost = getPacman().getNearestGhostToPursue(game, position, lastMove);
		if(GhostTarget==null) {
			GhostTarget = nGhost;
		}else
		if(!game.isGhostEdible(GhostTarget.getName())) {
			GhostTarget = nGhost;
		}
		if(getPacman().getDebug()) System.out.println("MsPacman is chasing ghost: "+GhostTarget.getName().name());
		return game.getNextMoveTowardsTarget(position, GhostTarget.getPosition(), lastMove, DM.PATH);
	}
}

class RandomMove extends PacmanAction {
	private Random random = new Random();
	public RandomMove(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}

	@Override
	public MOVE apply(Game game) {
		int position = getPacman().getPosition(game);
		MOVE lastMove = getPacman().getLastMove(game);
		MOVE[] moves = game.getPossibleMoves(position, lastMove);
		return moves[random.nextInt(moves.length)];
	}
}

//STATES
class Greedy extends PacmanState {
	private PacmanRule r1 = new NearToPowerPill(getPacman());
	private PacmanRule r2 = new NearToGhosts(getPacman());
	private PacmanRule r3 = new Hunger(getPacman());
	private PacmanAction a1 = new AvoidPowerPill(getPacman());
	private PacmanAction a2 = new RandomMove(getPacman());
	private PacmanAction a3 = new GoToPowerPill(getPacman());
	public Greedy(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public void check(Game game) {//First rule is most important
		if(r2.evaluate(game)) {
			getPacman().changeState(new Aware(getPacman()));
			return;
		}
	}
	@Override
	public MOVE execute(Game game) {//First rule -> action is most important
		/*
		MOVE lastMove = game.getPacmanLastMoveMade();
		MOVE nextMove = lastMove;
		int position = game.getPacmanCurrentNodeIndex();
		*/
		//Decisions
		r3.evaluate(game);
		//if(!game.isJunction(position)) return nextMove;
		if(r3.evaluate(game)) return a3.apply(game);
		if(r1.evaluate(game)) return a1.apply(game);
		return a2.apply(game);
	}
	@Override
	public void notifyState() {
		System.out.println("PACMAN: STATE GREEDY ENABLED");
	}	
}

class Aware extends PacmanState {
	private PacmanRule r1 = new Eaten(getPacman());
	private PacmanRule r2 = new Powered(getPacman());
	private PacmanAction a1 = new RunAway(getPacman());
	public Aware(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public void check(Game game) {
		if(r1.evaluate(game)) {
			getPacman().changeState(new Greedy(getPacman()));
			return;
		}
		if(r2.evaluate(game)) {
			getPacman().changeState(new Pursuit(getPacman()));
			return;
		}
	}

	@Override
	public MOVE execute(Game game) {
		/*
		MOVE lastMove = game.getPacmanLastMoveMade();
		MOVE nextMove = lastMove;
		int position = game.getPacmanCurrentNodeIndex();
		if(!game.isJunction(position)) return nextMove;
		*/
		//Decisions
		return a1.apply(game);
	}

	@Override
	public void notifyState() {
		System.out.println("PACMAN: STATE AWARE ENABLED");
	}
}

class Pursuit extends PacmanState {
	private PacmanRule r1 = new Eaten(getPacman());
	private PacmanRule r2 = new NearToGhosts(getPacman());
	private PacmanRule r3 = new NoVulnerableGhosts(getPacman());
	private PacmanRule r4 = new NearToGhostLairHazard(getPacman());
	private PacmanAction a1 = new ChaseGhost(getPacman());
	private PacmanAction a2 = new RunAway(getPacman());
	public Pursuit(Es_MsGrasa_2026_pacman_v1 pacman) {
		super(pacman);
	}
	@Override
	public void check(Game game) {
		if(r1.evaluate(game)) {
			getPacman().changeState(new Greedy(getPacman()));
			return;
		}
		if(r3.evaluate(game)) {
			getPacman().changeState(new Greedy(getPacman()));
		}
	}
	@Override
	public MOVE execute(Game game) {
		/*
		MOVE lastMove = game.getPacmanLastMoveMade();
		MOVE nextMove = lastMove;
		int position = game.getPacmanCurrentNodeIndex();
		if(!game.isJunction(position)) return nextMove;
		*/ 
		if(r2.evaluate(game)) return a2.apply(game);
		if(r4.evaluate(game)) return a2.apply(game);
		return a1.apply(game);
	}
	@Override
	public void notifyState() {
		System.out.println("PACMAN: STATE PURSUIT ENABLED");
	}
	
}
