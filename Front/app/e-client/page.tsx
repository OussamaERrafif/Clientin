"use client"

import type React from "react"

import { useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Upload, Camera, Star, Coins, Trophy, Clock, CheckCircle, XCircle } from "lucide-react"

const submissionHistory = [
  {
    id: 1,
    restaurant: "Restaurant Al Fassia",
    date: "2024-01-15",
    status: "approved",
    points: 50,
    rating: 5,
    comment: "Service excellent, ambiance parfaite!",
  },
  {
    id: 2,
    restaurant: "Café Central",
    date: "2024-01-14",
    status: "pending",
    points: 0,
    rating: 4,
    comment: "Bon café, service rapide.",
  },
  {
    id: 3,
    restaurant: "Restaurant Marrakech",
    date: "2024-01-12",
    status: "rejected",
    points: 0,
    rating: 2,
    comment: "Service décevant.",
    rejectionReason: "Commentaire trop court",
  },
]

const achievements = [
  { name: "Premier Avis", icon: Trophy, earned: true, description: "Votre premier feedback approuvé" },
  { name: "Critique Expert", icon: Star, earned: true, description: "10 avis approuvés" },
  { name: "Photographe", icon: Camera, earned: false, description: "Ajoutez 5 photos à vos avis" },
  { name: "Régulier", icon: Clock, earned: false, description: "Soumettez un avis chaque semaine pendant un mois" },
]

export default function EClientPortal() {
  const [activeTab, setActiveTab] = useState("submit")
  const [rating, setRating] = useState(0)
  const [comment, setComment] = useState("")
  const [restaurantName, setRestaurantName] = useState("")
  const [photos, setPhotos] = useState<File[]>([])
  const [totalPoints, setTotalPoints] = useState(250)

  const handlePhotoUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(event.target.files || [])
    setPhotos([...photos, ...files])
  }

  const handleSubmit = () => {
    // Simulate submission
    alert("Feedback soumis avec succès! En attente de validation.")
    setRating(0)
    setComment("")
    setRestaurantName("")
    setPhotos([])
  }

  const getStatusIcon = (status: string) => {
    switch (status) {
      case "approved":
        return <CheckCircle className="h-4 w-4 text-green-500" />
      case "pending":
        return <Clock className="h-4 w-4 text-yellow-500" />
      case "rejected":
        return <XCircle className="h-4 w-4 text-red-500" />
      default:
        return null
    }
  }

  const getStatusText = (status: string) => {
    switch (status) {
      case "approved":
        return "Approuvé"
      case "pending":
        return "En attente"
      case "rejected":
        return "Refusé"
      default:
        return "Inconnu"
    }
  }

  const getStatusColor = (status: string) => {
    switch (status) {
      case "approved":
        return "bg-green-600"
      case "pending":
        return "bg-yellow-600"
      case "rejected":
        return "bg-red-600"
      default:
        return "bg-gray-600"
    }
  }

  return (
    <div className="min-h-screen bg-black p-6">
      {/* Header */}
      <div className="max-w-6xl mx-auto">
        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-3xl font-bold text-white">E-Client Portal</h1>
            <p className="text-gray-400">Partagez vos expériences et gagnez des récompenses</p>
          </div>
          <div className="flex items-center gap-4">
            <Card className="bg-gradient-to-r from-purple-600 to-purple-800 border-purple-600">
              <CardContent className="p-4 flex items-center gap-3">
                <Coins className="h-8 w-8 text-yellow-400" />
                <div>
                  <p className="text-purple-100 text-sm">Points Total</p>
                  <p className="text-white text-xl font-bold">{totalPoints}</p>
                </div>
              </CardContent>
            </Card>
            <Avatar className="h-12 w-12">
              <AvatarImage src="/placeholder.svg?height=48&width=48&text=E" />
              <AvatarFallback>EC</AvatarFallback>
            </Avatar>
          </div>
        </div>

        <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
          <TabsList className="grid w-full grid-cols-4 bg-gray-800 mb-8">
            <TabsTrigger value="submit" className="text-white">
              Soumettre Avis
            </TabsTrigger>
            <TabsTrigger value="history" className="text-white">
              Historique
            </TabsTrigger>
            <TabsTrigger value="rewards" className="text-white">
              Récompenses
            </TabsTrigger>
            <TabsTrigger value="achievements" className="text-white">
              Badges
            </TabsTrigger>
          </TabsList>

          <TabsContent value="submit" className="space-y-6">
            <Card className="bg-gray-900 border-gray-800">
              <CardHeader>
                <CardTitle className="text-white">Soumettre un Nouveau Feedback</CardTitle>
              </CardHeader>
              <CardContent className="space-y-6">
                <div>
                  <Label htmlFor="restaurant" className="text-white font-medium mb-2 block">
                    Nom du Restaurant *
                  </Label>
                  <Input
                    id="restaurant"
                    placeholder="Ex: Restaurant Al Fassia"
                    value={restaurantName}
                    onChange={(e) => setRestaurantName(e.target.value)}
                    className="bg-gray-800 border-gray-700 text-white"
                  />
                </div>

                <div>
                  <Label className="text-white font-medium mb-3 block">Note *</Label>
                  <div className="flex gap-2 mb-2">
                    {[1, 2, 3, 4, 5].map((star) => (
                      <button
                        key={star}
                        onClick={() => setRating(star)}
                        className="transition-transform hover:scale-110"
                      >
                        <Star
                          className={`h-8 w-8 ${star <= rating ? "text-yellow-400 fill-current" : "text-gray-600"}`}
                        />
                      </button>
                    ))}
                  </div>
                </div>

                <div>
                  <Label htmlFor="comment" className="text-white font-medium mb-2 block">
                    Votre Avis *
                  </Label>
                  <Textarea
                    id="comment"
                    placeholder="Décrivez votre expérience en détail (minimum 50 caractères)..."
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    className="bg-gray-800 border-gray-700 text-white min-h-[120px]"
                  />
                  <p className="text-gray-400 text-sm mt-1">{comment.length}/50 caractères minimum</p>
                </div>

                <div>
                  <Label className="text-white font-medium mb-2 block">Photos/Vidéos (optionnel)</Label>
                  <div className="border-2 border-dashed border-gray-700 rounded-lg p-6 text-center">
                    <input
                      type="file"
                      multiple
                      accept="image/*,video/*"
                      onChange={handlePhotoUpload}
                      className="hidden"
                      id="media-upload"
                    />
                    <label htmlFor="media-upload" className="cursor-pointer">
                      <Upload className="h-8 w-8 text-gray-400 mx-auto mb-2" />
                      <p className="text-gray-400">Cliquez pour ajouter des photos ou vidéos</p>
                      <p className="text-gray-500 text-sm">+10 points bonus pour chaque média</p>
                    </label>
                  </div>
                  {photos.length > 0 && (
                    <div className="mt-3">
                      <p className="text-white text-sm mb-2">{photos.length} fichier(s) sélectionné(s)</p>
                      <div className="flex flex-wrap gap-2">
                        {photos.map((photo, index) => (
                          <Badge key={index} variant="outline" className="text-purple-400 border-purple-400">
                            {photo.name}
                          </Badge>
                        ))}
                      </div>
                    </div>
                  )}
                </div>

                <div className="bg-gray-800 p-4 rounded-lg">
                  <h4 className="text-white font-medium mb-2">Récompenses Potentielles</h4>
                  <div className="space-y-1 text-sm">
                    <div className="flex justify-between text-gray-300">
                      <span>Avis de base:</span>
                      <span className="text-green-400">+25 points</span>
                    </div>
                    <div className="flex justify-between text-gray-300">
                      <span>Avis détaillé (50+ caractères):</span>
                      <span className="text-green-400">+15 points</span>
                    </div>
                    <div className="flex justify-between text-gray-300">
                      <span>Photos/Vidéos ({photos.length}):</span>
                      <span className="text-green-400">+{photos.length * 10} points</span>
                    </div>
                    <div className="border-t border-gray-700 pt-2 mt-2">
                      <div className="flex justify-between text-white font-medium">
                        <span>Total estimé:</span>
                        <span className="text-green-400">
                          +{25 + (comment.length >= 50 ? 15 : 0) + photos.length * 10} points
                        </span>
                      </div>
                    </div>
                  </div>
                </div>

                <Button
                  onClick={handleSubmit}
                  disabled={!restaurantName || rating === 0 || comment.length < 50}
                  className="w-full bg-purple-600 hover:bg-purple-700 disabled:opacity-50 py-3"
                >
                  Soumettre le Feedback
                </Button>
              </CardContent>
            </Card>
          </TabsContent>

          <TabsContent value="history" className="space-y-6">
            <Card className="bg-gray-900 border-gray-800">
              <CardHeader>
                <CardTitle className="text-white">Historique des Soumissions</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {submissionHistory.map((submission) => (
                    <div key={submission.id} className="p-4 bg-gray-800 rounded-lg">
                      <div className="flex items-center justify-between mb-3">
                        <div>
                          <h4 className="text-white font-medium">{submission.restaurant}</h4>
                          <p className="text-gray-400 text-sm">{submission.date}</p>
                        </div>
                        <div className="flex items-center gap-3">
                          <Badge className={getStatusColor(submission.status)}>
                            {getStatusIcon(submission.status)}
                            <span className="ml-1">{getStatusText(submission.status)}</span>
                          </Badge>
                          {submission.points > 0 && <Badge className="bg-green-600">+{submission.points} pts</Badge>}
                        </div>
                      </div>
                      <div className="flex items-center gap-2 mb-2">
                        {[1, 2, 3, 4, 5].map((star) => (
                          <Star
                            key={star}
                            className={`h-4 w-4 ${
                              star <= submission.rating ? "text-yellow-400 fill-current" : "text-gray-600"
                            }`}
                          />
                        ))}
                      </div>
                      <p className="text-gray-300 text-sm">{submission.comment}</p>
                      {submission.status === "rejected" && submission.rejectionReason && (
                        <div className="mt-3 p-2 bg-red-900/20 border border-red-800 rounded">
                          <p className="text-red-400 text-sm">
                            <strong>Raison du refus:</strong> {submission.rejectionReason}
                          </p>
                        </div>
                      )}
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </TabsContent>

          <TabsContent value="rewards" className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <Card className="bg-gray-900 border-gray-800">
                <CardHeader>
                  <CardTitle className="text-white">Convertir en Espèces</CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                  <div className="text-center">
                    <div className="text-3xl font-bold text-white mb-2">{totalPoints}</div>
                    <div className="text-gray-400">Points disponibles</div>
                  </div>
                  <div className="space-y-3">
                    <div className="flex justify-between items-center p-3 bg-gray-800 rounded">
                      <span className="text-white">100 points</span>
                      <span className="text-green-400">= 10 MAD</span>
                    </div>
                    <div className="flex justify-between items-center p-3 bg-gray-800 rounded">
                      <span className="text-white">250 points</span>
                      <span className="text-green-400">= 30 MAD</span>
                    </div>
                    <div className="flex justify-between items-center p-3 bg-gray-800 rounded">
                      <span className="text-white">500 points</span>
                      <span className="text-green-400">= 75 MAD</span>
                    </div>
                  </div>
                  <Button className="w-full bg-green-600 hover:bg-green-700">Convertir 250 points → 30 MAD</Button>
                </CardContent>
              </Card>

              <Card className="bg-gray-900 border-gray-800">
                <CardHeader>
                  <CardTitle className="text-white">Portefeuille Numérique</CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                  <div className="space-y-3">
                    <div>
                      <Label className="text-white">Méthode de paiement</Label>
                      <select className="w-full mt-1 p-2 bg-gray-800 border border-gray-700 rounded text-white">
                        <option>Compte bancaire</option>
                        <option>PayPal</option>
                        <option>Portefeuille mobile</option>
                      </select>
                    </div>
                    <div>
                      <Label className="text-white">Numéro de compte</Label>
                      <Input
                        placeholder="Entrez votre numéro de compte"
                        className="bg-gray-800 border-gray-700 text-white"
                      />
                    </div>
                  </div>
                  <div className="bg-gray-800 p-3 rounded">
                    <h4 className="text-white font-medium mb-2">Historique des Retraits</h4>
                    <div className="space-y-2 text-sm">
                      <div className="flex justify-between">
                        <span className="text-gray-300">15 Jan 2024</span>
                        <span className="text-green-400">+30 MAD</span>
                      </div>
                      <div className="flex justify-between">
                        <span className="text-gray-300">01 Jan 2024</span>
                        <span className="text-green-400">+20 MAD</span>
                      </div>
                    </div>
                  </div>
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          <TabsContent value="achievements" className="space-y-6">
            <Card className="bg-gray-900 border-gray-800">
              <CardHeader>
                <CardTitle className="text-white">Badges et Réalisations</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {achievements.map((achievement, index) => (
                    <div
                      key={index}
                      className={`p-4 rounded-lg border-2 ${
                        achievement.earned ? "bg-purple-900/20 border-purple-600" : "bg-gray-800 border-gray-700"
                      }`}
                    >
                      <div className="flex items-center gap-3 mb-2">
                        <achievement.icon
                          className={`h-8 w-8 ${achievement.earned ? "text-purple-400" : "text-gray-500"}`}
                        />
                        <div>
                          <h4 className={`font-medium ${achievement.earned ? "text-white" : "text-gray-400"}`}>
                            {achievement.name}
                          </h4>
                          {achievement.earned && <Badge className="bg-purple-600 text-xs">Obtenu</Badge>}
                        </div>
                      </div>
                      <p className={`text-sm ${achievement.earned ? "text-gray-300" : "text-gray-500"}`}>
                        {achievement.description}
                      </p>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </TabsContent>
        </Tabs>
      </div>
    </div>
  )
}
