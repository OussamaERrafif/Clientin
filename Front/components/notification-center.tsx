"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Card, CardContent } from "@/components/ui/card"
import { Bell, CheckCircle, AlertTriangle, Info } from "lucide-react"
import { DropdownMenu, DropdownMenuContent, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"

const notifications = [
  {
    id: 1,
    type: "success",
    title: "Nouveau feedback positif",
    message: "Restaurant Al Fassia a reçu un avis 5 étoiles",
    time: "Il y a 5 minutes",
    read: false,
  },
  {
    id: 2,
    type: "warning",
    title: "Feedback négatif",
    message: "Attention: avis 2 étoiles pour Café Central",
    time: "Il y a 15 minutes",
    read: false,
  },
  {
    id: 3,
    type: "info",
    title: "Objectif atteint",
    message: "Sarah Khalil a atteint son objectif mensuel",
    time: "Il y a 1 heure",
    read: true,
  },
  {
    id: 4,
    type: "info",
    title: "Abonnement",
    message: "Votre abonnement expire dans 7 jours",
    time: "Il y a 2 heures",
    read: true,
  },
]

export function NotificationCenter() {
  const [notificationList, setNotificationList] = useState(notifications)
  const unreadCount = notificationList.filter((n) => !n.read).length

  const markAsRead = (id: number) => {
    setNotificationList((prev) => prev.map((n) => (n.id === id ? { ...n, read: true } : n)))
  }

  const markAllAsRead = () => {
    setNotificationList((prev) => prev.map((n) => ({ ...n, read: true })))
  }

  const getIcon = (type: string) => {
    switch (type) {
      case "success":
        return <CheckCircle className="h-4 w-4 text-green-500" />
      case "warning":
        return <AlertTriangle className="h-4 w-4 text-yellow-500" />
      case "info":
        return <Info className="h-4 w-4 text-blue-500" />
      default:
        return <Info className="h-4 w-4 text-gray-500" />
    }
  }

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="ghost" size="icon" className="relative text-gray-400 hover:text-white">
          <Bell className="h-5 w-5" />
          {unreadCount > 0 && (
            <Badge className="absolute -top-1 -right-1 h-5 w-5 p-0 flex items-center justify-center bg-red-600 text-xs">
              {unreadCount}
            </Badge>
          )}
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end" className="w-80 bg-gray-900 border-gray-800">
        <div className="p-4">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-white font-semibold">Notifications</h3>
            {unreadCount > 0 && (
              <Button
                variant="ghost"
                size="sm"
                onClick={markAllAsRead}
                className="text-purple-400 hover:text-purple-300"
              >
                Tout marquer comme lu
              </Button>
            )}
          </div>
          <div className="space-y-2 max-h-96 overflow-y-auto">
            {notificationList.map((notification) => (
              <Card
                key={notification.id}
                className={`bg-gray-800 border-gray-700 cursor-pointer transition-colors ${
                  !notification.read ? "border-purple-600" : ""
                }`}
                onClick={() => markAsRead(notification.id)}
              >
                <CardContent className="p-3">
                  <div className="flex items-start gap-3">
                    {getIcon(notification.type)}
                    <div className="flex-1 min-w-0">
                      <div className="flex items-center justify-between">
                        <p className={`text-sm font-medium ${!notification.read ? "text-white" : "text-gray-300"}`}>
                          {notification.title}
                        </p>
                        {!notification.read && <div className="w-2 h-2 bg-purple-600 rounded-full flex-shrink-0" />}
                      </div>
                      <p className="text-gray-400 text-xs mt-1">{notification.message}</p>
                      <p className="text-gray-500 text-xs mt-1">{notification.time}</p>
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}
