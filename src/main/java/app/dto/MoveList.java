package app.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author dion
 */
public class MoveList {

    List<Move> moves;

    public MoveList() {
        moves = new ArrayList<>();
    }

    
    
    public MoveList(Collection<Move> values) {
        moves = new ArrayList<>();
        moves.addAll(values);
    }

    public static MoveList merge(Collection<MoveList> values)
    {
        MoveList moveList = new MoveList();
        moveList.moves = new ArrayList<>();
        values.forEach((value) -> {
            if (value != null) {
                moveList.moves.addAll(value.getMoves());
            }
        });
        return moveList;
    }
    
    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Move getMove(int i) {
        return moves.get(i);
    }

    public void remove(int i) {
        moves.remove(i);
    }

}
