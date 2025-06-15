package com.unl.music.base.models;

public enum TipoArchivoEnum {
    // Formatos Físicos
    CD("CD Físico"),
    VINILO("Disco de Vinilo"),
    CASSETTE("Cassette"),
    
    // Formatos Virtuales
    MP3("Archivo MP3"),
    WAV("Archivo WAV"),
    FLAC("Archivo FLAC"),
    STREAMING("Streaming");

    private final String descripcion;

    TipoArchivoEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isFisico() {
        return this == CD || this == VINILO || this == CASSETTE;
    }

    public boolean isVirtual() {
        return !isFisico();
    }
}
