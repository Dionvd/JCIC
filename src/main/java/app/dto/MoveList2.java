package app.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is used to send a JSON object that contains moves, as opposed to
 * a raw list of moves where data might be lost on transfer. Has a round ID
 * compared to MoveList.
 * @author dion
 */
public class MoveList2 {

    private List<Move> moves;
    private long id;

    public MoveList2(long id) {
        this.id = id;
        moves = new ArrayList<>();
    }
    
    public MoveList2(long id, Collection<Move> values) {
        this.id = id;
        moves = new ArrayList<>();
        moves.addAll(values);
    }

    public static MoveList2 merge(Collection<MoveList2> values)
    {
        MoveList2 moveList = new MoveList2(0);
        moveList.moves = new ArrayList<>();
        values.forEach((value) -> {
            if (value != null) {
                moveList.setId(value.getId());
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
}
