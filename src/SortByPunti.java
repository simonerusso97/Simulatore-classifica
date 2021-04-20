import java.util.Comparator;

class SortByPunti implements Comparator<Squadra>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Squadra a, Squadra b)
    {
        return b.punti - a.punti;
    }
}
