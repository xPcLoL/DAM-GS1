"use client";

import { useState, useEffect } from "react";
import { supabase } from "../../lib/supabase";
import { Plus, Edit3, Trash2, Shield, Anchor, UploadCloud, Search, Skull } from "lucide-react";

export default function AdminPage() {
  const [productos, setProductos] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    id_producto: null as any,
    nombre: "",
    precio: "",
    descripcion: "",
    imagen: "",
    id_categoria: "" as any,
  });
  const [categorias, setCategorias] = useState<any[]>([]);

  const [isEditing, setIsEditing] = useState(false);
  const [uploadingImage, setUploadingImage] = useState(false);
  const [saveSuccess, setSaveSuccess] = useState(false);

  useEffect(() => {
    fetchProductos();
    fetchCategorias();
  }, []);

  const fetchCategorias = async () => {
    try {
      const response = await fetch("/api/categoria");
      const data = await response.json();
      setCategorias(data || []);
    } catch (error) {
      console.error("Error al obtener categorías:", error);
    }
  };

  const fetchProductos = async () => {
    setLoading(true);
    try {
      const response = await fetch("/api/producto");
      const data = await response.json();
      // ORDENAR POR RECOMPENSA
      const sorted = (data || []).sort((a: any, b: any) => (b.precio || 0) - (a.precio || 0));
      setProductos(sorted);
    } catch (error) {
      console.error("Error:", error);
    }
    setLoading(false);
  };

  const handleFileSelect = async (e: any) => {
    const file = e.target.files[0];
    if (!file) return;
    setUploadingImage(true);
    try {
      const fileExt = file.name.split(".").pop();
      const fileName = `${Math.random()}.${fileExt}`;
      const { error: uploadError } = await supabase.storage.from("imagenes-productos").upload(fileName, file);
      if (uploadError) throw uploadError;
      const { data } = supabase.storage.from("imagenes-productos").getPublicUrl(fileName);
      setFormData((prev) => ({ ...prev, imagen: data.publicUrl }));
    } catch (error) {
      console.error(error);
    } finally {
      setUploadingImage(false);
    }
  };

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    try {
      const response = await fetch("/api/producto", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          ...formData,
          precio: parseFloat(formData.precio),
          id_categoria: formData.id_categoria ? parseInt(formData.id_categoria) : null,
        }),
      });
      if (!response.ok) throw new Error("Error saving");
      setSaveSuccess(true);
      setTimeout(() => setSaveSuccess(false), 3000);
      resetForm();
      fetchProductos();
    } catch (error) {
      console.error(error);
    }
  };

  const handleEdit = (producto: any) => {
    setFormData({
      id_producto: producto.id_producto,
      nombre: producto.nombre,
      precio: producto.precio,
      descripcion: producto.descripcion || "",
      imagen: producto.imagen || "",
      id_categoria: producto.id_categoria || "",
    });
    setIsEditing(true);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const handleDelete = async (id: any) => {
    if (confirm("¿Eliminar este registro oficial?")) {
      await fetch(`/api/producto?id=${id}`, { method: "DELETE" });
      fetchProductos();
    }
  };

  const resetForm = () => {
    setFormData({ id_producto: null, nombre: "", precio: "", descripcion: "", imagen: "", id_categoria: "" });
    setIsEditing(false);
  };

  return (
    <div className="min-h-screen pt-32 pb-20">
      <div className="max-w-7xl mx-auto px-6">
        
        {/* Header Section */}
        <div className="flex flex-col md:flex-row justify-between items-start md:items-end gap-6 mb-12">
          <div>
            <div className="flex items-center gap-2 text-[#0077be] font-bold text-xs uppercase tracking-[0.3em] mb-2">
              <Shield className="w-4 h-4" />
              <span>Acceso Restringido — Marines</span>
            </div>
            <h1 className="font-bangers text-6xl text-[#003366] tracking-tight uppercase">Cuartel General</h1>
          </div>
          
          <div className="bg-[#fdf5e6] p-4 rounded-xl shadow-lg border-2 border-[#0077be]/20 flex items-center gap-4 px-8">
            <div className="text-right">
              <p className="font-bangers text-xl text-[#003366]">{productos.length} FICHADOS</p>
              <p className="text-[10px] font-black text-gray-400 uppercase tracking-widest">Base de Datos Mundial</p>
            </div>
            <Anchor className="text-[#0077be] w-8 h-8" />
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12">
          
          {/* Form Column */}
          <div className="lg:col-span-4">
            <div className="bg-[#fdf5e6] rounded-2xl overflow-hidden shadow-xl border-2 border-[#003366]/10 sticky top-32">
              <div className="bg-[#003366] p-6 text-white text-center">
                <h2 className="font-bangers text-3xl tracking-widest">{isEditing ? 'EDITAR FICHA' : 'NUEVA FICHA'}</h2>
              </div>

              <form onSubmit={handleSubmit} className="p-8 space-y-6">
                <div>
                  <label className="block text-[10px] font-black text-[#2b1e16] uppercase tracking-widest mb-2">Nombre del Sujeto</label>
                  <input 
                    type="text" 
                    value={formData.nombre}
                    onChange={(e) => setFormData({...formData, nombre: e.target.value})}
                    className="w-full px-4 py-3 bg-white/50 border-2 border-[#2b1e16]/10 rounded-lg focus:border-[#0077be] outline-none font-fell"
                    required
                  />
                </div>

                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-[10px] font-black text-[#2b1e16] uppercase tracking-widest mb-2">Bounty (฿)</label>
                    <input 
                      type="number" 
                      value={formData.precio}
                      onChange={(e) => setFormData({...formData, precio: e.target.value})}
                      className="w-full px-4 py-3 bg-white/50 border-2 border-[#2b1e16]/10 rounded-lg focus:border-[#0077be] outline-none font-fell"
                      required
                    />
                  </div>
                  <div>
                    <label className="block text-[10px] font-black text-[#2b1e16] uppercase tracking-widest mb-2">Filiación</label>
                    <select 
                      value={formData.id_categoria}
                      onChange={(e) => setFormData({...formData, id_categoria: e.target.value})}
                      className="w-full px-4 py-3 bg-white/50 border-2 border-[#2b1e16]/10 rounded-lg focus:border-[#0077be] outline-none font-fell"
                      required
                    >
                      <option value="">Seleccionar...</option>
                      {categorias.map(c => <option key={c.id_categoria} value={c.id_categoria}>{c.nombre}</option>)}
                    </select>
                  </div>
                </div>

                <div>
                  <label className="block text-[10px] font-black text-[#2b1e16] uppercase tracking-widest mb-2">Descripción del Peligro</label>
                  <textarea 
                    value={formData.descripcion}
                    onChange={(e) => setFormData({...formData, descripcion: e.target.value})}
                    className="w-full px-4 py-3 bg-white/50 border-2 border-[#2b1e16]/10 rounded-lg focus:border-[#0077be] outline-none h-24 resize-none font-fell"
                  />
                </div>

                <div>
                   <label className="block text-[10px] font-black text-[#2b1e16] uppercase tracking-widest mb-2">Retrato</label>
                   <div className="flex items-center gap-4">
                      <div className="w-20 h-20 bg-white rounded-lg border-2 border-[#2b1e16]/10 flex items-center justify-center overflow-hidden">
                        {formData.imagen ? <img src={formData.imagen} className="w-full h-full object-cover" /> : <Skull className="text-gray-200" />}
                      </div>
                      <div className="flex-grow">
                        <label className="block w-full text-center py-2 bg-[#0077be] text-white rounded-lg cursor-pointer hover:bg-[#003366] transition-colors text-[10px] font-black uppercase tracking-widest">
                          {uploadingImage ? 'SUBIENDO...' : 'SUBIR FOTO'}
                          <input type="file" className="hidden" onChange={handleFileSelect} />
                        </label>
                      </div>
                   </div>
                </div>

                <div className="flex gap-4 pt-4">
                  <button 
                    type="submit"
                    className="flex-grow py-4 bg-[#8B0000] text-white font-bangers text-2xl tracking-widest rounded hover:bg-[#003366] transition-all shadow-xl"
                  >
                    {isEditing ? 'ACTUALIZAR' : 'REGISTRAR'}
                  </button>
                  {isEditing && (
                    <button type="button" onClick={resetForm} className="px-6 py-4 bg-gray-200 text-gray-500 rounded hover:bg-gray-300 font-bold">✕</button>
                  )}
                </div>
              </form>
            </div>
          </div>

          {/* List Column */}
          <div className="lg:col-span-8">
            <div className="bg-[#fdf5e6] rounded-2xl shadow-xl border-2 border-[#003366]/10 overflow-hidden flex flex-col max-h-[800px]">
              <div className="bg-[#003366] p-4 text-white font-bangers text-xl tracking-widest flex items-center gap-3 flex-shrink-0">
                <Search className="w-5 h-5" />
                REGISTROS ACTUALES
              </div>
              
              <div className="divide-y divide-[#2b1e16]/10 overflow-y-auto custom-scrollbar">
                {loading ? (
                   Array(5).fill(0).map((_, i) => <div key={i} className="p-8 h-20 bg-white/30 animate-pulse" />)
                ) : (
                  productos.map(p => (
                    <div key={p.id_producto} className="p-4 flex items-center gap-6 hover:bg-white/50 transition-colors group">
                      <div className="w-20 h-20 flex-shrink-0 border-2 border-[#2b1e16]/10 rounded-lg overflow-hidden shadow-sm">
                        <img src={p.imagen} alt={p.nombre} className="w-full h-full object-cover" />
                      </div>
                      
                      <div className="flex-grow">
                        <div className="flex items-center gap-3 mb-1">
                          <h3 className="font-bangers text-3xl text-[#003366] tracking-tight">{p.nombre}</h3>
                          <span className="text-[10px] font-black uppercase tracking-widest px-2 py-0.5 bg-[#0077be]/10 text-[#0077be] rounded border border-[#0077be]/20">
                            {categorias.find(c => c.id_categoria === p.id_categoria)?.nombre || 'SIN FILIACIÓN'}
                          </span>
                        </div>
                        <p className="text-[#8B0000] font-bangers text-2xl leading-none">฿ {p.precio.toLocaleString()}-</p>
                      </div>

                      <div className="flex gap-2">
                        <button 
                          onClick={() => handleEdit(p)} 
                          className="p-3 bg-white border border-[#0077be]/20 text-[#0077be] rounded-xl hover:bg-[#0077be] hover:text-white transition-all shadow-sm"
                          title="Editar"
                        >
                          <Edit3 className="w-5 h-5" />
                        </button>
                        <button 
                          onClick={() => handleDelete(p.id_producto)} 
                          className="p-3 bg-white border border-red-200 text-red-500 rounded-xl hover:bg-red-500 hover:text-white transition-all shadow-sm"
                          title="Eliminar"
                        >
                          <Trash2 className="w-5 h-5" />
                        </button>
                      </div>
                    </div>
                  ))
                )}
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}
