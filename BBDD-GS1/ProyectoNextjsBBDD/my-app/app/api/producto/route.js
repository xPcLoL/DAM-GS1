import { supabase } from "@/lib/supabase";

// Obtener todos los productos con su categoría
export async function GET() {
  const { data, error } = await supabase
    .from("productos")
    .select("*, categorias(nombre)")
    .order("fecha_creacion", { ascending: false });

  if (error) {
    return Response.json({ error: error.message }, { status: 500 });
  }

  return Response.json(data);
}

// Crear o Actualizar un producto
export async function POST(request) {
  const data = await request.json();
  const { id_producto, nombre, precio, descripcion, imagen, id_categoria } = data;

  if (id_producto) {
    // Si viene ID, es una actualización
    const { data: updatedData, error } = await supabase
      .from("productos")
      .update({ nombre, precio, descripcion, imagen, id_categoria })
      .eq("id_producto", id_producto)
      .select();

    if (error) return Response.json({ error: error.message }, { status: 500 });
    return Response.json(updatedData[0]);
  } else {
    // Si no viene ID, es una creación
    const { data: insertedData, error } = await supabase
      .from("productos")
      .insert([{ nombre, precio, descripcion, imagen, id_categoria }])
      .select();

    if (error) return Response.json({ error: error.message }, { status: 500 });
    return Response.json(insertedData[0]);
  }
}

// Eliminar un producto
export async function DELETE(request) {
  const { searchParams } = new URL(request.url);
  const id = searchParams.get("id");

  if (!id) {
    return Response.json({ error: "Falta el ID del producto" }, { status: 400 });
  }

  const { error } = await supabase
    .from("productos")
    .delete()
    .eq("id_producto", id);

  if (error) {
    return Response.json({ error: error.message }, { status: 500 });
  }

  return Response.json({ mensaje: "Producto eliminado correctamente" });
}