export interface ClienteSolicitudRequest {

    titulo: string;

    descripcion: string;

    prioridad:
        | "BAJA"
        | "MEDIA"
        | "ALTA";

    gravedad:
        | "LEVE"
        | "MODERADA"
        | "GRAVE"
        | "CRITICA";
}