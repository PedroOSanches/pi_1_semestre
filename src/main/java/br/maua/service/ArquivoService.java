package br.maua.service;

import br.maua.exception.UpdateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

public class ArquivoService {
    public static File salvarArquivo(File origem, File destino) throws UpdateException {
        try {

            java.io.File pastaDestino = destino.getParentFile();
            if (pastaDestino != null && !pastaDestino.exists()) {
                pastaDestino.mkdirs();
            }

            java.nio.file.Files.copy(
                    origem.toPath(),
                    destino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
            return destino;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UpdateException("Erro ao tentar salvar arquivo" );
        }
    }
}
