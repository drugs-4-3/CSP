import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class LatinSquareDomain {

    private int dimension;
    private int domains_number;
    private LinkedList<Integer>[][] domains;

    public LatinSquareDomain(int dimension) {
        this.dimension = dimension;
        this.domains_number = dimension*dimension;

        domains = new LinkedList[dimension][dimension];
        initialFillDomains();
    }

    public void cutDomains(int index, int value) {

        int x = index%dimension;
        int y = index/dimension;

        for (int i = 0; i < dimension; i++) {
            LinkedList<Integer> single_domain_x = domains[x][i];
            LinkedList<Integer> single_domain_y = domains[i][y];

            int ix = single_domain_x.indexOf(value);
            if (ix > -1) {
                single_domain_x.remove(ix);
            }

            int yx = single_domain_y.indexOf(value);
            if (yx > -1) {
                single_domain_y.remove(ix);
            }
        }
    }

    public LinkedList<Integer>getDomain(int index) {

        int x = index%dimension;
        int y = index/dimension;
        return domains[x][y];
    }

    private void initialFillDomains() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                LinkedList<Integer> single_domain = new LinkedList<>();
                for (int k = 1; k <= dimension; k++) {
                    single_domain.add(k);
                }
                domains[i][j] = single_domain;
            }
        }
    }

    public void removeOneFromDomain(int index, int value) {
        int x = index%dimension;
        int y = index/dimension;
        LinkedList<Integer> single_domain = domains[x][y];
        int i = single_domain.indexOf(value);
        if (i > -1) {
            single_domain.remove(i);
        }
    }
}
