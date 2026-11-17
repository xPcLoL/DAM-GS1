"use client";

import { useState, useEffect } from 'react';
import "./globals.css";
import Link from 'next/link';
import { usePathname, useRouter } from 'next/navigation';
import { createBrowserClient } from '@supabase/ssr'
import { LogOut, LogIn, Ship, Anchor, Menu, X, Skull } from 'lucide-react'
import { AuthProvider, useAuth } from '@/components/AuthContext';
import LoginModal from '@/components/LoginModal';

export default function RootLayout({ children }: {
  children: React.ReactNode;
}) {
  return (
    <AuthProvider>
      <LayoutContent>{children}</LayoutContent>
    </AuthProvider>
  );
}

function LayoutContent({ children }: { children: React.ReactNode }) {
  const { openLoginModal } = useAuth();
  const pathname = usePathname();
  const router = useRouter();
  const [scrolled, setScrolled] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);
  const [user, setUser] = useState<any>(null);

  const supabase = createBrowserClient(
    process.env.NEXT_PUBLIC_SUPABASE_URL!,
    process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY!
  )

  useEffect(() => {
    const handleScroll = () => {
      setScrolled(window.scrollY > 20);
    };
    window.addEventListener('scroll', handleScroll);
    
    const getUser = async () => {
      const { data: { user } } = await supabase.auth.getUser()
      setUser(user)
    }
    getUser()

    const { data: { subscription } } = supabase.auth.onAuthStateChange((_event, session) => {
      setUser(session?.user ?? null)
    })

    return () => {
      window.removeEventListener('scroll', handleScroll);
      subscription.unsubscribe()
    }
  }, [supabase]);

  const handleLogout = async () => {
    await supabase.auth.signOut()
    router.push('/')
    router.refresh()
  }

  const isAdmin = user?.email === 'andresivan200015@gmail.com'

  const navLinks = [
    { href: '/', label: 'Inicio' },
    { href: '/recompensas', label: 'Recompensas' },
    { href: '/mugiwaras', label: 'Tripulación' },
  ];

  return (
    <html lang="es">
      <head>
        <title>Bounty Board — Grand Line</title>
        <meta name="description" content="El tablero oficial de recompensas piratas." />
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin="anonymous" />
        <link href="https://fonts.googleapis.com/css2?family=Bangers&family=Cinzel:wght@400;700;900&display=swap" rel="stylesheet" />
      </head>
      <body className="flex flex-col min-h-screen text-[#001f3f] bg-white">
        
        {/* ─── OCEANIC HEADER ─── */}
        <header 
          className={`fixed top-0 left-0 right-0 z-50 transition-all duration-300 py-3 shadow-xl`}
          style={{
            background: 'linear-gradient(to bottom, #fdf5e6 0%, #f5deb3 100%)',
            borderBottom: '3px solid #0077be',
          }}
        >
          {/* Top rope detail */}
          <div className="absolute top-0 left-0 right-0 h-1 bg-repeating-linear-gradient(90deg, #003366 0px, #003366 4px, #0077be 4px, #0077be 8px)" />
          
          <nav className="max-w-7xl mx-auto px-6 flex justify-between items-center">
            {/* Logo */}
            <Link href="/" className="flex items-center gap-2 group">
              <Ship className="w-8 h-8 text-[#0077be]" />
              <div>
                <div className="font-bangers text-3xl leading-none text-[#003366]">
                  GRAND LINE RECORDS
                </div>
                <div className="font-cinzel text-[10px] tracking-widest uppercase text-[#0077be]">
                  Grand Line Archive
                </div>
              </div>
            </Link>

            {/* Desktop Nav */}
            <div className="hidden md:flex items-center gap-8">
              {navLinks.map((link) => (
                <Link
                  key={link.href}
                  href={link.href}
                  className={`font-cinzel text-xs font-black uppercase tracking-widest transition-all ${
                    pathname === link.href
                      ? 'text-[#0077be] border-b-2 border-[#0077be]'
                      : 'text-[#003366] hover:text-[#0077be]'
                  }`}
                >
                  {link.label}
                </Link>
              ))}

              {isAdmin && (
                <Link
                  href="/admin"
                  className="font-bangers text-lg px-6 py-1.5 bg-[#003366] text-white rounded-full hover:bg-[#001f3f] transition-all shadow-md"
                >
                  CUARTEL
                </Link>
              )}

              {user ? (
                <button
                  onClick={handleLogout}
                  className="flex items-center gap-2 px-4 py-1.5 bg-red-500 text-white rounded-full hover:bg-red-600 transition-all text-[10px] font-bold shadow-sm"
                >
                  <LogOut className="w-3 h-3" />
                  SALIR
                </button>
              ) : (
                <button
                  onClick={openLoginModal}
                  className="flex items-center gap-2 px-6 py-2 rounded-full transition-all font-cinzel text-xs font-black shadow-lg border-2 bg-[#0077be] text-white border-[#0077be] hover:bg-[#003366]"
                >
                  <LogIn className="w-4 h-4" />
                  INGRESAR
                </button>
              )}
            </div>

            <button 
              className="md:hidden text-[#003366]"
              onClick={() => setMenuOpen(!menuOpen)}
            >
              {menuOpen ? <X /> : <Menu />}
            </button>
          </nav>

          {/* Mobile menu */}
          {menuOpen && (
            <div className="md:hidden bg-white py-6 px-6 flex flex-col gap-6 shadow-2xl animate-fade-in">
               {navLinks.map((link) => (
                <Link
                  key={link.href}
                  href={link.href}
                  onClick={() => setMenuOpen(false)}
                  className="font-cinzel text-lg font-bold uppercase text-[#003366]"
                >
                  {link.label}
                </Link>
              ))}
              {user ? (
                <button onClick={handleLogout} className="w-full py-3 bg-red-500 text-white rounded-lg">SALIR</button>
              ) : (
                <button onClick={openLoginModal} className="w-full py-3 bg-[#0077be] text-white rounded-lg">INGRESAR</button>
              )}
            </div>
          )}
        </header>

        <main className="flex-grow">
          {children}
        </main>

        {/* ─── OCEANIC FOOTER ─── */}
        <footer className="bg-[#001f3f] text-white pt-20 pb-10 overflow-hidden border-t-4 border-[#0077be]">
          <div className="max-w-7xl mx-auto px-6 relative z-10">
            <div className="grid grid-cols-1 md:grid-cols-4 gap-12 mb-12">
              <div className="col-span-1 md:col-span-2">
                <div className="flex items-center gap-3 mb-6">
                  <Anchor className="w-8 h-8 text-[#0077be]" />
                  <span className="font-bangers text-4xl tracking-widest text-white">BOUNTY BOARD</span>
                </div>
                <p className="font-fell text-lg text-white/60 max-w-md leading-relaxed italic">
                  "¿Mis tesoros? Si los queréis, os los daré... ¡Buscadlos! ¡Lo dejé todo en ese lugar!"
                </p>
              </div>
              
              <div>
                <h4 className="font-bangers text-xl mb-6 text-[#0077be]">NAVEGACIÓN</h4>
                <ul className="space-y-4 font-cinzel text-xs font-bold tracking-widest">
                  <li><Link href="/recompensas" className="hover:text-[#0077be] transition-colors">RECOMPENSAS</Link></li>
                  <li><Link href="/mugiwaras" className="hover:text-[#0077be] transition-colors">TRIPULACIÓN</Link></li>
                  <li><Link href="/" className="hover:text-[#0077be] transition-colors">INICIO</Link></li>
                </ul>
              </div>
              
              <div>
                <h4 className="font-bangers text-xl mb-6 text-[#0077be]">CONECTAR</h4>
                <div className="flex gap-4">
                  <div className="w-10 h-10 rounded-full bg-white/5 border border-white/10 flex items-center justify-center hover:bg-[#0077be] transition-all cursor-pointer">
                    <Skull className="w-5 h-5" />
                  </div>
                  <div className="w-10 h-10 rounded-full bg-white/5 border border-white/10 flex items-center justify-center hover:bg-[#0077be] transition-all cursor-pointer">
                    <Ship className="w-5 h-5" />
                  </div>
                </div>
              </div>
            </div>
            
            <div className="pt-10 border-t border-white/10 flex flex-col md:flex-row justify-between items-center gap-6 text-[#0077be]">
              <p className="font-cinzel text-[10px] tracking-[0.3em] uppercase">
                © {new Date().getFullYear()} BOUNTY BOARD — GRAN LINE ARCHIVES
              </p>
              <div className="flex gap-8 font-cinzel text-[10px] font-black tracking-widest">
                <span className="cursor-pointer hover:text-white transition-colors">PRIVACIDAD</span>
                <span className="cursor-pointer hover:text-white transition-colors">TÉRMINOS</span>
              </div>
            </div>
          </div>
        </footer>
        
        <LoginModal />
      </body>
    </html>
  );
}
