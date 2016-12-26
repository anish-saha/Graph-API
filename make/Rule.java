package make;

import java.util.ArrayList;
import java.util.List;

import static make.Main.error;

/** Represents the rules concerning a single target in a makefile.
 *  @author P. N. Hilfinger
 */
class Rule {
    /** A new Rule for TARGET. Adds corresponding vertex to MAKER's dependence
     *  graph. */
    Rule(Maker maker, String target) {
        _maker = maker;
        _depends = _maker.getGraph();
        _target = target;
        _vertex = _depends.add(this);
        _time = _maker.getInitialAge(target);
        _finished = false;
    }

    /** Add the target of DEPENDENT to my dependencies. */
    void addDependency(Rule dependent) {
        if (dependent == null || dependent._vertex == 0) {
            error("error, Invalid vertex passed in.");
        }
        _depends.add(dependent);
        _depends.add(getVertex(), dependent.getVertex());
    }

    /** Add COMMANDS to my command set.  Signals IllegalStateException if
     *  COMMANDS is non-empty, but I already have a non-empty command set.
     */
    void addCommands(List<String> commands) {
        try {
            if (_commands.size() != 0 && commands.size() != 0) {
                error("error, Input and commands are non-empty.");
            }
        } catch (IllegalStateException e) {
            error("error, Illegal action.");
        }
        for (String c : commands) {
            _commands.add(c);
        }
    }

    /** Return the vertex representing me. */
    int getVertex() {
        return _vertex;
    }

    /** Return my target. */
    String getTarget() {
        return _target;
    }

    /** Return my target's current change time. */
    Integer getTime() {
        return _time;
    }

    /** Return true iff I have not yet been brought up to date. */
    boolean isUnfinished() {
        return !_finished;
    }

    /** Check that dependencies are in fact built before it's time to rebuild
     *  a node. */
    private void checkFinishedDependencies() {
        boolean built = true;
        for (int i : _depends.successors(getVertex())) {
            if (_depends.getLabel(i).isUnfinished()) {
                built = false;
            }
        }
        if (!built) {
            error("error, Dependencies have not been built.");
        }
    }

    /** Return true iff I am out of date and need to be rebuilt (including the
     *  case where I do not exist).  Assumes that my dependencies are all
     *  successfully rebuilt. */
    private boolean outOfDate() {
        if (!(_time == null)) {
            boolean outDated = false;
            for (int i : _depends.successors(getVertex())) {
                if ((_depends.getLabel(i).getTime() == null)
                    || (_depends.getLabel(i).getTime() > _time)) {
                    outDated = true;
                }
            }
            return outDated;
        }
        return true;
    }

    /** Rebuild me, if needed, after checking that all dependencies are rebuilt
     *  (error otherwise). */
    void rebuild() {
        checkFinishedDependencies();

        if (outOfDate()) {
            if (_commands.isEmpty()) {
                error("error, %s needs to be rebuilt, but has no commands",
                      _target);
            }
            _time = _maker.getCurrentTime();
            for (String c : _commands) {
                System.out.println(c);
            }
        }
        _finished = true;
    }

    /** The Maker that created me. */
    private Maker _maker;
    /** The Maker's dependency graph. */
    private Depends _depends;
    /** My target. */
    private String _target;
    /** The vertex corresponding to my target. */
    private int _vertex;
    /** My command list. */
    private ArrayList<String> _commands = new ArrayList<>();
    /** True iff I have been brought up to date. */
    private boolean _finished;
    /** My change time, or null if I don't exist. */
    private Integer _time;
}
