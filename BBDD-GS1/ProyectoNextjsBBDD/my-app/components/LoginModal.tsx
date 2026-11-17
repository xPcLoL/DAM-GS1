"use client";

import { createBrowserClient } from '@supabase/ssr';
import { LogIn, X } from 'lucide-react';
import { useState } from 'react';
import { useAuth } from './AuthContext';

export default function LoginModal() {
  const { isLoginModalOpen, closeLoginModal } = useAuth();
  const [loading, setLoading] = useState(false);

  const supabase = createBrowserClient(
    process.env.NEXT_PUBLIC_SUPABASE_URL!,
    process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY!
  );

  const handleGoogleLogin = async () => {
    setLoading(true);
    try {
      const { error } = await supabase.auth.signInWithOAuth({
        provider: 'google',
        options: {
          redirectTo: `${window.location.origin}/api/auth/callback`,
        },
      });
      if (error) throw error;
    } catch (error) {
      console.error('Error:', error);
      setLoading(false);
    }
  };

  if (!isLoginModalOpen) return null;

  return (
    <div className="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-[#001f3f]/80 backdrop-blur-sm animate-fade-in">
      <div className="relative w-full max-w-md bg-white border-2 border-[#0077be] rounded-2xl shadow-[0_20px_50px_rgba(0,51,102,0.3)] overflow-hidden">
        {/* Header Decor */}
        <div className="h-2 w-full bg-[#0077be]" />
        
        <button 
          onClick={closeLoginModal}
          className="absolute top-4 right-4 text-gray-400 hover:text-[#003366] transition-colors"
        >
          <X className="w-6 h-6" />
        </button>

        <div className="p-8 text-center">
          <div className="mb-6 flex justify-center">
            <div className="w-16 h-16 bg-[#e0f7fa] rounded-full flex items-center justify-center text-[#0077be] border border-[#0077be]/20">
              <LogIn className="w-8 h-8" />
            </div>
          </div>

          <h2 className="font-bangers text-4xl text-[#003366] mb-2 tracking-tight">INGRESAR A LA FLOTA</h2>
          <p className="font-fell italic text-gray-500 mb-8">Inicia sesión para registrar tus hazañas y valorar a otros piratas.</p>

          <button
            onClick={handleGoogleLogin}
            disabled={loading}
            className="w-full flex items-center justify-center gap-4 bg-[#f8fbff] hover:bg-[#e0f7fa] text-[#003366] py-4 rounded-xl transition-all transform hover:scale-[1.02] border border-[#e0f7fa] shadow-sm disabled:opacity-50"
          >
            {loading ? (
              <span className="animate-pulse font-bold">CONECTANDO...</span>
            ) : (
              <>
                <img src="https://www.google.com/favicon.ico" alt="Google" className="w-5 h-5" />
                <span className="font-bold text-sm tracking-tight uppercase">Continuar con Google</span>
              </>
            )}
          </button>

          <p className="mt-8 text-[10px] text-gray-500 uppercase tracking-widest font-cinzel">
            ⚓ Gobierno Mundial — Terminal de Acceso ⚓
          </p>
        </div>
      </div>
    </div>
  );
}
