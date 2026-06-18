import easyaccept.EasyAccept;

/**
 * Ponto de entrada da aplicação usada para executar os casos de teste do EasyAccept.
 * Os Arquivos para os testes estão localizados na pasta `tests`.
 */
public class Main{
    /**
     * Entrada de execução dos scripts de teste EasyAccept.
     */
    public static void main(String[] args){
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us1_1.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us1_2.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us2_1.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us2_2.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us3_1.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us3_2.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us4_1.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us4_2.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us5_1.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us5_2.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us6_1.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us6_2.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us7_1.txt"});
        EasyAccept.main(new String[]{"br.ufal.ic.p2.jackut.Facade", "tests/us7_2.txt"});
    }
}