"use client"

import { useState } from "react"
import { Camera, Mail, Bell, Shield, Palette, CreditCard, Activity } from "lucide-react"

const activityLogs = [
  {
    id: 1,
    action: "Connexion au système",
    timestamp: "2024-01-15 14:30:25",
    ip: "192.168.1.100",
    device: "Chrome sur Windows",
  },
  {
    id: 2,
    action: "Modification du profil",
    timestamp: "2024-01-15 11:20:15",
    ip: "192.168.1.100",
    device: "Chrome sur Windows",
  },
  {
    id: 3,
    action: "Changement de mot de passe",
    timestamp: "2024-01-14 16:45:30",
    ip: "192.168.1.100",
    device: "Chrome sur Windows",
  },
  {
    id: 4,
    action: "Activation des notifications email",
    timestamp: "2024-01-14 09:15:45",
    ip: "192.168.1.100",
    device: "Chrome sur Windows",
  },
  {
    id: 5,
    action: "Consultation des feedbacks",
    timestamp: "2024-01-13 18:22:10",
    ip: "192.168.1.100",
    device: "Chrome sur Windows",
  },
]

export default function SettingsPage() {
  const [activeTab, setActiveTab] = useState("profile")
  const [darkMode, setDarkMode] = useState(true)
  const [emailNotifications, setEmailNotifications] = useState(true)
  const [pushNotifications, setPushNotifications] = useState(true)
  const [weeklyReports, setWeeklyReports] = useState(false)

  const tabs = [
    { id: "profile", label: "Profil", icon: Shield },
    { id: "notifications", label: "Notifications", icon: Bell },
    { id: "subscription", label: "Abonnement", icon: CreditCard },
    { id: "appearance", label: "Apparence", icon: Palette },
    { id: "activity", label: "Activité", icon: Activity },
  ]

  return (
    <div className="flex-1 bg-black p-6">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-white mb-2">Paramètres</h1>
        <p className="text-gray-400">Gérez vos préférences et paramètres de compte</p>
      </div>

      <div className="flex gap-8">
        {/* Sidebar Navigation */}
        <div className="w-64 flex-shrink-0">
          <nav className="space-y-2">
            {tabs.map((tab) => (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg text-left transition-colors ${
                  activeTab === tab.id ? "bg-purple-600 text-white" : "text-gray-300 hover:bg-gray-800 hover:text-white"
                }`}
              >
                <tab.icon className="h-5 w-5" />
                {tab.label}
              </button>
            ))}
          </nav>
        </div>

        {/* Content Area */}
        <div className="flex-1">
          {activeTab === "profile" && (
            <div className="space-y-8">
              <div className="bg-gray-900 rounded-lg p-6 border border-gray-800">
                <h2 className="text-xl font-bold text-white mb-6">Informations du Profil</h2>

                {/* Profile Photo */}
                <div className="flex items-center gap-6 mb-8">
                  <div className="relative">
                    <div className="w-24 h-24 bg-gray-700 rounded-full flex items-center justify-center text-white text-2xl font-bold">
                      AD
                    </div>
                    <button className="absolute -bottom-2 -right-2 w-8 h-8 bg-purple-600 rounded-full flex items-center justify-center hover:bg-purple-700 transition-colors">
                      <Camera className="h-4 w-4 text-white" />
                    </button>
                  </div>
                  <div>
                    <h3 className="text-white font-semibold text-lg">Mohamed Benali</h3>
                    <p className="text-gray-400">Administrateur</p>
                    <div className="mt-2 px-3 py-1 bg-green-600 text-white text-sm rounded-full inline-block">
                      Compte Actif
                    </div>
                  </div>
                </div>

                {/* Form Fields */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
                  <div>
                    <label className="block text-white font-medium mb-2">Prénom</label>
                    <input
                      type="text"
                      defaultValue="Mohamed"
                      className="w-full p-3 bg-gray-800 border border-gray-700 rounded-lg text-white focus:border-purple-500 focus:outline-none"
                    />
                  </div>
                  <div>
                    <label className="block text-white font-medium mb-2">Nom</label>
                    <input
                      type="text"
                      defaultValue="Benali"
                      className="w-full p-3 bg-gray-800 border border-gray-700 rounded-lg text-white focus:border-purple-500 focus:outline-none"
                    />
                  </div>
                  <div>
                    <label className="block text-white font-medium mb-2">Email</label>
                    <input
                      type="email"
                      defaultValue="mohamed.benali@clientin.com"
                      className="w-full p-3 bg-gray-800 border border-gray-700 rounded-lg text-white focus:border-purple-500 focus:outline-none"
                    />
                  </div>
                  <div>
                    <label className="block text-white font-medium mb-2">Téléphone</label>
                    <input
                      type="tel"
                      defaultValue="+212 6 12 34 56 78"
                      className="w-full p-3 bg-gray-800 border border-gray-700 rounded-lg text-white focus:border-purple-500 focus:outline-none"
                    />
                  </div>
                </div>

                {/* Password Section */}
                <div className="border-t border-gray-800 pt-6">
                  <h4 className="text-white font-medium mb-4">Changer le mot de passe</h4>
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
                    <div>
                      <label className="block text-white font-medium mb-2">Mot de passe actuel</label>
                      <input
                        type="password"
                        className="w-full p-3 bg-gray-800 border border-gray-700 rounded-lg text-white focus:border-purple-500 focus:outline-none"
                      />
                    </div>
                    <div>
                      <label className="block text-white font-medium mb-2">Nouveau mot de passe</label>
                      <input
                        type="password"
                        className="w-full p-3 bg-gray-800 border border-gray-700 rounded-lg text-white focus:border-purple-500 focus:outline-none"
                      />
                    </div>
                  </div>
                </div>

                <button className="bg-purple-600 hover:bg-purple-700 text-white px-6 py-3 rounded-lg font-medium transition-colors">
                  Sauvegarder les modifications
                </button>
              </div>
            </div>
          )}

          {activeTab === "notifications" && (
            <div className="bg-gray-900 rounded-lg p-6 border border-gray-800">
              <h2 className="text-xl font-bold text-white mb-6">Préférences de Notification</h2>

              <div className="space-y-6">
                <div className="flex items-center justify-between p-4 bg-gray-800 rounded-lg">
                  <div className="flex items-center gap-3">
                    <Mail className="h-5 w-5 text-gray-400" />
                    <div>
                      <p className="text-white font-medium">Notifications par email</p>
                      <p className="text-gray-400 text-sm">Recevoir des alertes par email</p>
                    </div>
                  </div>
                  <label className="relative inline-flex items-center cursor-pointer">
                    <input
                      type="checkbox"
                      checked={emailNotifications}
                      onChange={(e) => setEmailNotifications(e.target.checked)}
                      className="sr-only peer"
                    />
                    <div className="w-11 h-6 bg-gray-600 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-purple-600"></div>
                  </label>
                </div>

                <div className="flex items-center justify-between p-4 bg-gray-800 rounded-lg">
                  <div className="flex items-center gap-3">
                    <Bell className="h-5 w-5 text-gray-400" />
                    <div>
                      <p className="text-white font-medium">Notifications push</p>
                      <p className="text-gray-400 text-sm">Notifications en temps réel dans l'app</p>
                    </div>
                  </div>
                  <label className="relative inline-flex items-center cursor-pointer">
                    <input
                      type="checkbox"
                      checked={pushNotifications}
                      onChange={(e) => setPushNotifications(e.target.checked)}
                      className="sr-only peer"
                    />
                    <div className="w-11 h-6 bg-gray-600 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-purple-600"></div>
                  </label>
                </div>

                <div className="flex items-center justify-between p-4 bg-gray-800 rounded-lg">
                  <div className="flex items-center gap-3">
                    <Shield className="h-5 w-5 text-gray-400" />
                    <div>
                      <p className="text-white font-medium">Rapports hebdomadaires</p>
                      <p className="text-gray-400 text-sm">Résumé des performances chaque semaine</p>
                    </div>
                  </div>
                  <label className="relative inline-flex items-center cursor-pointer">
                    <input
                      type="checkbox"
                      checked={weeklyReports}
                      onChange={(e) => setWeeklyReports(e.target.checked)}
                      className="sr-only peer"
                    />
                    <div className="w-11 h-6 bg-gray-600 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-purple-600"></div>
                  </label>
                </div>
              </div>
            </div>
          )}

          {activeTab === "subscription" && (
            <div className="space-y-6">
              <div className="bg-gradient-to-br from-purple-600 to-purple-800 rounded-lg p-6">
                <h2 className="text-xl font-bold text-white mb-4">Plan Actuel</h2>
                <div className="flex items-center justify-between mb-4">
                  <h3 className="text-white text-xl font-bold">PREMIUM</h3>
                  <div className="bg-white text-purple-800 px-3 py-1 rounded-full text-sm font-medium">Actif</div>
                </div>
                <div className="grid grid-cols-2 gap-4 text-white">
                  <div>
                    <p className="text-purple-100">Type d'abonnement</p>
                    <p className="font-semibold">Mensuel</p>
                  </div>
                  <div>
                    <p className="text-purple-100">Prix</p>
                    <p className="font-semibold">299 MAD/mois</p>
                  </div>
                  <div>
                    <p className="text-purple-100">Date d'expiration</p>
                    <p className="font-semibold">25 Mars 2024</p>
                  </div>
                  <div>
                    <p className="text-purple-100">Renouvellement</p>
                    <p className="font-semibold">Automatique</p>
                  </div>
                </div>
              </div>

              <div className="bg-gray-900 rounded-lg p-6 border border-gray-800">
                <h3 className="text-white font-medium mb-4">Options de mise à niveau</h3>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="bg-gray-800 p-4 rounded-lg">
                    <h4 className="text-white font-semibold mb-2">Clientin Pro</h4>
                    <p className="text-gray-400 text-sm mb-3">
                      Rapports avancés, intégrations CRM, support prioritaire
                    </p>
                    <p className="text-white font-bold text-lg mb-3">499 MAD/mois</p>
                    <button className="w-full bg-purple-600 hover:bg-purple-700 text-white py-2 rounded-lg transition-colors">
                      Mettre à niveau
                    </button>
                  </div>
                  <div className="bg-gray-800 p-4 rounded-lg">
                    <h4 className="text-white font-semibold mb-2">Enterprise</h4>
                    <p className="text-gray-400 text-sm mb-3">Solution complète pour grandes entreprises</p>
                    <p className="text-white font-bold text-lg mb-3">Sur devis</p>
                    <button className="w-full border border-gray-600 text-gray-300 py-2 rounded-lg hover:bg-gray-700 transition-colors">
                      Nous contacter
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}

          {activeTab === "appearance" && (
            <div className="bg-gray-900 rounded-lg p-6 border border-gray-800">
              <h2 className="text-xl font-bold text-white mb-6">Préférences d'Apparence</h2>

              <div className="space-y-6">
                <div className="flex items-center justify-between p-4 bg-gray-800 rounded-lg">
                  <div className="flex items-center gap-3">
                    <Palette className="h-5 w-5 text-gray-400" />
                    <div>
                      <p className="text-white font-medium">Mode sombre</p>
                      <p className="text-gray-400 text-sm">Interface avec thème sombre</p>
                    </div>
                  </div>
                  <label className="relative inline-flex items-center cursor-pointer">
                    <input
                      type="checkbox"
                      checked={darkMode}
                      onChange={(e) => setDarkMode(e.target.checked)}
                      className="sr-only peer"
                    />
                    <div className="w-11 h-6 bg-gray-600 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-purple-600"></div>
                  </label>
                </div>

                <div className="p-4 bg-gray-800 rounded-lg">
                  <h4 className="text-white font-medium mb-4">Couleur d'accent</h4>
                  <div className="flex gap-3">
                    {[
                      { name: "Purple", color: "bg-purple-600", active: true },
                      { name: "Blue", color: "bg-blue-600", active: false },
                      { name: "Green", color: "bg-green-600", active: false },
                      { name: "Red", color: "bg-red-600", active: false },
                    ].map((color) => (
                      <button
                        key={color.name}
                        className={`w-8 h-8 rounded-full ${color.color} ${
                          color.active ? "ring-2 ring-white ring-offset-2 ring-offset-gray-800" : ""
                        }`}
                      />
                    ))}
                  </div>
                </div>

                <div className="p-4 bg-gray-800 rounded-lg">
                  <h4 className="text-white font-medium mb-4">Langue</h4>
                  <select className="w-full p-3 bg-gray-700 border border-gray-600 rounded-lg text-white focus:border-purple-500 focus:outline-none">
                    <option value="fr">Français</option>
                    <option value="ar">العربية</option>
                    <option value="en">English</option>
                  </select>
                </div>
              </div>
            </div>
          )}

          {activeTab === "activity" && (
            <div className="bg-gray-900 rounded-lg p-6 border border-gray-800">
              <h2 className="text-xl font-bold text-white mb-6">Journal d'Activité</h2>

              <div className="space-y-4">
                {activityLogs.map((log) => (
                  <div key={log.id} className="flex items-center justify-between p-4 bg-gray-800 rounded-lg">
                    <div className="flex items-center gap-4">
                      <div className="w-10 h-10 bg-purple-600 rounded-full flex items-center justify-center">
                        <Activity className="h-5 w-5 text-white" />
                      </div>
                      <div>
                        <p className="text-white font-medium">{log.action}</p>
                        <p className="text-gray-400 text-sm">{log.device}</p>
                      </div>
                    </div>
                    <div className="text-right">
                      <p className="text-gray-300 text-sm">{log.timestamp}</p>
                      <p className="text-gray-500 text-xs">{log.ip}</p>
                    </div>
                  </div>
                ))}
              </div>

              <div className="mt-6 p-4 bg-gray-800 rounded-lg">
                <h4 className="text-white font-medium mb-2">Paramètres de sécurité</h4>
                <p className="text-gray-400 text-sm mb-4">Gérez vos sessions actives et l'historique de connexion</p>
                <div className="flex gap-3">
                  <button className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-lg text-sm transition-colors">
                    Déconnecter tous les appareils
                  </button>
                  <button className="border border-gray-600 text-gray-300 px-4 py-2 rounded-lg text-sm hover:bg-gray-700 transition-colors">
                    Exporter l'historique
                  </button>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}
