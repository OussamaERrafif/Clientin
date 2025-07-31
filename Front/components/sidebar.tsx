"use client"
import Link from "next/link"
import { usePathname } from "next/navigation"
import {
  LayoutDashboard,
  Users,
  MessageSquare,
  BarChart3,
  Settings,
  ChevronRight,
  Smartphone,
  Shield,
  Wifi,
} from "lucide-react"
import { cn } from "@/lib/utils"
import { NotificationCenter } from "./notification-center"

const navigation = [
  { name: "Dashboard", href: "/", icon: LayoutDashboard },
  { name: "Employés", href: "/employees", icon: Users },
  { name: "Feedbacks", href: "/feedbacks", icon: MessageSquare },
  { name: "Insight", href: "/insights", icon: BarChart3 },
  { name: "E-Client", href: "/e-client", icon: Smartphone },
  { name: "Admin", href: "/admin", icon: Shield },
  { name: "NFC Feedback", href: "/nfc-feedback", icon: Wifi },
  { name: "Paramètres", href: "/settings", icon: Settings },
]

export function Sidebar() {
  const pathname = usePathname()

  return (
    <div className="flex h-screen w-64 flex-col bg-gray-900 border-r border-gray-800">
      {/* Header with Notifications */}
      <div className="flex h-16 items-center justify-between px-6 border-b border-gray-800">
        <h1 className="text-2xl font-bold text-white">
          Clientin<span className="text-purple-400">°</span>
        </h1>
        <NotificationCenter />
      </div>

      {/* Navigation */}
      <nav className="flex-1 space-y-2 px-4 py-6">
        {navigation.map((item) => {
          const isActive = pathname === item.href
          return (
            <Link
              key={item.name}
              href={item.href}
              className={cn(
                "flex items-center gap-3 rounded-xl px-4 py-3 text-sm font-medium transition-all duration-200",
                isActive
                  ? "bg-purple-600 text-white shadow-lg shadow-purple-600/25"
                  : "text-gray-300 hover:bg-gray-800 hover:text-white",
              )}
            >
              <item.icon className="h-5 w-5" />
              {item.name}
            </Link>
          )
        })}
      </nav>

      {/* Upgrade Card */}
      <div className="mx-4 mb-6">
        <div className="rounded-xl bg-gradient-to-br from-purple-600 to-purple-800 p-6 shadow-xl">
          <h3 className="text-lg font-bold text-white mb-2">UPGRADE</h3>
          <h4 className="text-xl font-bold text-white mb-3">CLIENTIN PRO</h4>
          <p className="text-purple-100 text-sm mb-4">Débloquez des rapports avancés, des intégrations CRM...</p>
          <div className="flex items-center text-white font-medium">
            <ChevronRight className="h-4 w-4 mr-1" />
            <ChevronRight className="h-4 w-4 mr-1" />
            <ChevronRight className="h-4 w-4" />
          </div>
        </div>
      </div>
    </div>
  )
}
