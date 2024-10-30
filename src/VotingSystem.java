import java.util.*;
import java.io.*;

public class VotingSystem {
    public static final String EXIT_PROGRAM = "0";
    private Map<Integer, String> kandidat = new HashMap<>();
    private Map<String, Integer> voteCount = new HashMap<>();

    public void addCandidat(int nomer, String nama) {//rename
        getKandidat().put(nomer, nama);
        getVoteCount().put(nama, 0);
    }

    public void vote(int nomerKandidat) {
        if (getKandidat().containsKey(nomerKandidat)) {
            getVoteCount().put(getKandidat().get(nomerKandidat), getVoteCount().get(getKandidat().get(nomerKandidat)) + 1);//inline variabel
            System.out.println("Vote berhasil untuk " + getKandidat().get(nomerKandidat));
        } else {
            System.out.println("Nomor kandidat tidak ditemukan.");
        }
    }

    public void tampilkanHasil() {
        System.out.println("Hasil voting saat ini:");
        for (Map.Entry<String, Integer> entry : getVoteCount().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " suara");
        }
    }

    public void saveResults() {//exract method
        saveToFile();
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("hasil_voting.txt"))) {
            for (Map.Entry<String, Integer> entry : getVoteCount().entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + " suara");
                writer.newLine();
            }
            System.out.println("Hasil voting telah disimpan ke hasil_voting.txt");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan hasil voting: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VotingSystem votingSystem = new VotingSystem();

        inputKandidat(scanner, votingSystem);//extract method
        prosesVoting(scanner, votingSystem);//extract method
        
        votingSystem.tampilkanHasil();
        votingSystem.saveResults();

        scanner.close();
    }

    private static void prosesVoting(Scanner scanner, VotingSystem votingSystem) {
        System.out.println("Voting dimulai! Ketik '0' untuk mengakhiri dan 'hasil' untuk melihat hasil sementara.");
        while (true) {
            System.out.print("Masukkan nomor kandidat yang ingin Anda pilih: ");
            String input = scanner.nextLine();
            if (input.equals(EXIT_PROGRAM)) {//Replace Magic Number with Symbolic Constant(introduce constan)

                break;
            } else if (input.equals("hasil")) {
                votingSystem.tampilkanHasil();
            } else {
                try {
                    int voteInput = Integer.parseInt(input);
                    votingSystem.vote(voteInput);
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Silakan masukkan nomor kandidat atau 'hasil' untuk melihat hasil sementara.");
                }
            }
        }
    }

    private static void inputKandidat(Scanner scanner, VotingSystem votingSystem) {
        System.out.print("Masukkan jumlah kandidat: ");
        int jumKandidat = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < jumKandidat; i++) {
            System.out.print("Masukkan nama kandidat ke-" + (i + 1) + ": ");
            String namaKandidat = scanner.nextLine();
            votingSystem.addCandidat(i + 1, namaKandidat);
        }
    }

    //getter setter (encapsulation field)
    public Map<Integer, String> getKandidat() {
        return kandidat;
    }

    public void setKandidat(Map<Integer, String> kandidat) {
        this.kandidat = kandidat;
    }

    public Map<String, Integer> getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Map<String, Integer> voteCount) {
        this.voteCount = voteCount;
    }
}
