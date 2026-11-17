"use server";

import fs from "fs";
import path from "path";

export async function getCarouselImages() {
  try {
    const dir = path.join(process.cwd(), "public/images/carrusel");
    const files = fs.readdirSync(dir);
    
    // Filtrar solo imágenes (png, jpg, jpeg, webp) y ordenarlas alfabéticamente
    const images = files
      .filter((f) => /\.(png|jpe?g|webp)$/i.test(f))
      .sort()
      .map((f) => `/images/carrusel/${f}`);
      
    return images;
  } catch (error) {
    console.error("Error leyendo carpeta del carrusel:", error);
    return [];
  }
}
