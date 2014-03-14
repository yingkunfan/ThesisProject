package sriracha.simulator.model;

import sriracha.simulator.solver.analysis.ac.ACEquation;
import sriracha.simulator.solver.analysis.dc.DCEquation;
import sriracha.simulator.solver.analysis.trans.TransEquation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Subcircuits use a different internal node numbering system and maintains a mapping from internal nodes to
 * matrix indices
 */
public class SubCircuit extends CircuitElement
{

    /**
     * Template this subcircuit is based on
     */
    private SubCircuitTemplate template;

    /**
     * Node numbers for mapping from internal system to
     * the mapping from the internal node system is done implicitly
     * through the index.
     */
    private ArrayList<Integer> nodes;

    /**
     * elements forming up the subcircuit these will have node numbers corresponding
     * to the corresponding matrix index.
     */
    private HashMap<String, CircuitElement> elements;


    public SubCircuit(String name, SubCircuitTemplate template)
    {
        super(name);
        this.template = template;
        nodes = new ArrayList<Integer>();
        elements = new HashMap<String, CircuitElement>();
    }

    /**
     * Called once all the nodes have received a mapping
     * uses that information combined with the template
     * to finish setting up before applying stamps
     */

    public void expand()
    {
        elements.clear();
        for (CircuitElement e : template.getElements())
        {
            CircuitElement copy;

            //get referenced element for things like current controlled sources
            CircuitElement ref = e.getReferencedElement();
            //get new name
            String nname = nameElement(e.name);

            //if element does not have a reference element, make normal copy
            if (ref == null)
            {
                copy = e.buildCopy(nname, null);
            } else
            {
                //if there is a referenced element find the local copy for it.
                CircuitElement refCopy = elements.get(nameElement(ref.name));
                copy = e.buildCopy(nname, refCopy);
            }


            //get and apply node mapping
            int[] internal = e.getNodeIndices();


            int[] external = new int[internal.length];
            for (int i = 0; i < internal.length; i++)
            {
                external[i] = internal[i] == -1 ? -1 : nodes.get(internal[i]);

                /*if(internal[i] == 0)
                    external[i] = -1;
                else
                    external[i] = internal[i]; */
            }

            copy.setNodeIndices(external);

            //put new element in list.
            elements.put(copy.name, copy);

        }
    }

    /**
     * Creates a name for a local element based on the name the element had in the SubCircuitTemplate
     * and on the name that this instance of the subcircuit has
     *
     * @param templateElementName element name from template
     * @return new name for local copy
     */
    private String nameElement(String templateElementName)
    {
        return name + "_" + templateElementName;
    }


    @Override
    public void setNodeIndices(int... indices)
    {
        for (int i : indices)
        {
            nodes.add(i);
        }
    }

    @Override
    public int[] getNodeIndices()
    {
        int val[] = new int[nodes.size()], k = 0;
        for (Integer i : nodes) val[k++] = i;
        return val;
    }


    @Override
    public void applyAC(ACEquation equation)
    {
        for (CircuitElement e : elements.values())
        {
            e.applyAC(equation);
        }
    }

    @Override
    public void applyDC(DCEquation equation)
    {
        for (CircuitElement e : elements.values())
        {
            e.applyDC(equation);
        }
    }

    @Override
    public void applyTrans(TransEquation equation)
    {
        for (CircuitElement e : elements.values())
        {
            e.applyTrans(equation);
        }
    }

    @Override
    public void setFirstVarIndex(int i)
    {
        //add internal node mappings
        for (int b = 0; b < template.getInternalNodeCount(); b++)
        {
            nodes.add(i++);
        }
        //expand circuit
        expand();

        //assign indices for extra variables generated by internal elements
        for (CircuitElement e : elements.values())
        {
            if (e.getExtraVariableCount() > 0)
            {
                e.setFirstVarIndex(i);
                i += e.getExtraVariableCount();
            }
        }

    }

    /**
     * Number of External nodes only
     *
     * @return number of external ports
     */
    @Override
    public int getNodeCount()
    {
        return template.getExternalNodeCount();
    }

    /**
     * For subcircuits internal nodes count as extra variables
     *
     * @return number of additional variables required
     */
    @Override
    public int getExtraVariableCount()
    {
        int evCount = 0;

        for(CircuitElement e: template.getElements())
        {
            evCount += e.getExtraVariableCount();
        }
        return evCount + template.getInternalNodeCount();
    }

    /**
     * Makes a copy of the subcircuit
     *
     * @param name - the name for the copy.
     */
    @Override
    public SubCircuit buildCopy(String name, CircuitElement referencedElement)
    {
        return new SubCircuit(name, template);
    }

    /**
     * To string function
     * @return string description of the subcircuit
     */
    public String toString(){
        String s = "SubCircuit:\n";
        for (CircuitElement e : elements.values()) {
            s += e + "\n";
        }
        return s;
    }
}
