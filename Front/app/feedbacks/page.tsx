"use client"

import { useState } from "react"
import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Input } from "@/components/ui/input"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import { Search, Download, Star, Trash2, Eye } from "lucide-react"

const initialFeedbacks = [
  {
    id: 1,
    client: "Mohamed Benali",
    employee: "Sarah Khalil",
    date: "2024-01-15",
    time: "14:30",
    rating: 5,
    sentiment: "positive",
    comment:
      "Service excellent, très professionnel et rapide. Je recommande vivement! L'équipe était très accueillante et l'ambiance parfaite. La qualité de la nourriture était exceptionnelle.",
    category: "Service Client",
    restaurant: "Restaurant Al Fassia",
  },
  {
    id: 2,
    client: "Fatima Alami",
    employee: "Ahmed Tazi",
    date: "2024-01-15",
    time: "11:20",
    rating: 2,
    sentiment: "negative",
    comment:
      "Temps d'attente trop long, service décevant. Le personnel n'était pas très aimable et la nourriture était froide à l'arrivée.",
    category: "Support",
    restaurant: "Café Central",
  },
  {
    id: 3,
    client: "Youssef Mansouri",
    employee: "Mohamed Benali",
    date: "2024-01-14",
    time: "16:45",
    rating: 4,
    sentiment: "positive",
    comment:
      "Bon service dans l'ensemble, quelques améliorations possibles. L'ambiance était agréable et le personnel serviable.",
    category: "Qualité",
    restaurant: "Restaurant Marrakech",
  },
  {
    id: 4,
    client: "Aicha Berrada",
    employee: "Sarah Khalil",
    date: "2024-01-14",
    time: "09:15",
    rating: 5,
    sentiment: "positive",
    comment:
      "Parfait! L'équipe est très compétente et à l'écoute. Service rapide et efficace, je reviendrai certainement.",
    category: "Service Client",
    restaurant: "Restaurant Al Fassia",
  },
]

export default function FeedbacksPage() {
  const [feedbacks, setFeedbacks] = useState(initialFeedbacks)
  const [viewMode, setViewMode] = useState<"table" | "cards">("table")
  const [sentimentFilter, setSentimentFilter] = useState<"all" | "positive" | "negative">("all")
  const [searchTerm, setSearchTerm] = useState("")
  const [selectedFeedback, setSelectedFeedback] = useState<(typeof initialFeedbacks)[0] | null>(null)

  const filteredFeedbacks = feedbacks.filter((feedback) => {
    const matchesSearch =
      feedback.client.toLowerCase().includes(searchTerm.toLowerCase()) ||
      feedback.employee.toLowerCase().includes(searchTerm.toLowerCase()) ||
      feedback.comment.toLowerCase().includes(searchTerm.toLowerCase())
    const matchesSentiment = sentimentFilter === "all" || feedback.sentiment === sentimentFilter
    return matchesSearch && matchesSentiment
  })

  const getSentimentColor = (sentiment: string) => {
    return sentiment === "positive" ? "bg-green-600" : "bg-red-600"
  }

  const getSentimentText = (sentiment: string) => {
    return sentiment === "positive" ? "Positif" : "Négatif"
  }

  const renderStars = (rating: number) => {
    return Array.from({ length: 5 }, (_, i) => (
      <Star key={i} className={`h-4 w-4 ${i < rating ? "text-yellow-400 fill-current" : "text-gray-600"}`} />
    ))
  }

  const deleteFeedback = (id: number) => {
    if (confirm("Êtes-vous sûr de vouloir supprimer ce feedback ?")) {
      setFeedbacks((prev) => prev.filter((f) => f.id !== id))
      setSelectedFeedback(null)
    }
  }

  return (
    <div className="flex-1 bg-black p-6">
      {/* Header */}
      <div className="flex items-center justify-between mb-8">
        <h1 className="text-3xl font-bold text-white">Feedbacks</h1>
        <div className="flex gap-2">
          <Button variant="outline" className="border-gray-600 text-gray-300 bg-transparent">
            <Download className="h-4 w-4 mr-2" />
            Exporter CSV
          </Button>
          <Button
            variant={viewMode === "table" ? "default" : "outline"}
            onClick={() => setViewMode("table")}
            className={viewMode === "table" ? "bg-purple-600" : "border-gray-600 text-gray-300 bg-transparent"}
          >
            Tableau
          </Button>
          <Button
            variant={viewMode === "cards" ? "default" : "outline"}
            onClick={() => setViewMode("cards")}
            className={viewMode === "cards" ? "bg-purple-600" : "border-gray-600 text-gray-300 bg-transparent"}
          >
            Cartes
          </Button>
        </div>
      </div>

      {/* Filters */}
      <div className="flex gap-4 mb-6">
        <div className="relative flex-1 max-w-md">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
          <Input
            placeholder="Rechercher dans les feedbacks..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="pl-10 bg-gray-900 border-gray-700 text-white"
          />
        </div>
        <div className="flex gap-2">
          <Button
            variant={sentimentFilter === "positive" ? "default" : "outline"}
            size="sm"
            onClick={() => setSentimentFilter(sentimentFilter === "positive" ? "all" : "positive")}
            className={
              sentimentFilter === "positive"
                ? "bg-green-600 hover:bg-green-700"
                : "border-green-600 text-green-600 hover:bg-green-600 hover:text-white bg-transparent"
            }
          >
            Positifs
          </Button>
          <Button
            variant={sentimentFilter === "negative" ? "default" : "outline"}
            size="sm"
            onClick={() => setSentimentFilter(sentimentFilter === "negative" ? "all" : "negative")}
            className={
              sentimentFilter === "negative"
                ? "bg-red-600 hover:bg-red-700"
                : "border-red-600 text-red-600 hover:bg-red-600 hover:text-white bg-transparent"
            }
          >
            Négatifs
          </Button>
        </div>
      </div>

      {/* Content */}
      {viewMode === "table" ? (
        <Card className="bg-gray-900 border-gray-800">
          <CardContent className="p-0">
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead>
                  <tr className="border-b border-gray-800">
                    <th className="text-left p-4 text-gray-400 font-medium">Client</th>
                    <th className="text-left p-4 text-gray-400 font-medium">Employé</th>
                    <th className="text-left p-4 text-gray-400 font-medium">Date/Heure</th>
                    <th className="text-left p-4 text-gray-400 font-medium">Note</th>
                    <th className="text-left p-4 text-gray-400 font-medium">Sentiment</th>
                    <th className="text-left p-4 text-gray-400 font-medium">Commentaire</th>
                    <th className="text-left p-4 text-gray-400 font-medium">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredFeedbacks.map((feedback) => (
                    <tr key={feedback.id} className="border-b border-gray-800 hover:bg-gray-800/50">
                      <td className="p-4">
                        <div className="flex items-center gap-3">
                          <Avatar className="h-8 w-8">
                            <AvatarFallback>
                              {feedback.client
                                .split(" ")
                                .map((n) => n[0])
                                .join("")}
                            </AvatarFallback>
                          </Avatar>
                          <span className="text-white">{feedback.client}</span>
                        </div>
                      </td>
                      <td className="p-4 text-gray-300">{feedback.employee}</td>
                      <td className="p-4 text-gray-300">
                        <div>{feedback.date}</div>
                        <div className="text-sm text-gray-500">{feedback.time}</div>
                      </td>
                      <td className="p-4">
                        <div className="flex items-center gap-1">{renderStars(feedback.rating)}</div>
                      </td>
                      <td className="p-4">
                        <Badge className={getSentimentColor(feedback.sentiment)}>
                          {getSentimentText(feedback.sentiment)}
                        </Badge>
                      </td>
                      <td className="p-4 text-gray-300 max-w-xs truncate">{feedback.comment}</td>
                      <td className="p-4">
                        <div className="flex gap-2">
                          <Button
                            size="sm"
                            variant="outline"
                            onClick={() => setSelectedFeedback(feedback)}
                            className="border-gray-600 text-gray-300 bg-transparent hover:bg-gray-700"
                          >
                            <Eye className="h-4 w-4" />
                          </Button>
                          <Button
                            size="sm"
                            variant="outline"
                            onClick={() => deleteFeedback(feedback.id)}
                            className="border-red-600 text-red-400 bg-transparent hover:bg-red-600 hover:text-white"
                          >
                            <Trash2 className="h-4 w-4" />
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </CardContent>
        </Card>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredFeedbacks.map((feedback) => (
            <Card key={feedback.id} className="bg-gray-900 border-gray-800">
              <CardHeader>
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-3">
                    <Avatar className="h-10 w-10">
                      <AvatarFallback>
                        {feedback.client
                          .split(" ")
                          .map((n) => n[0])
                          .join("")}
                      </AvatarFallback>
                    </Avatar>
                    <div>
                      <h3 className="text-white font-medium">{feedback.client}</h3>
                      <p className="text-gray-400 text-sm">via {feedback.employee}</p>
                    </div>
                  </div>
                  <Badge className={getSentimentColor(feedback.sentiment)}>
                    {getSentimentText(feedback.sentiment)}
                  </Badge>
                </div>
              </CardHeader>
              <CardContent>
                <div className="space-y-3">
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-1">{renderStars(feedback.rating)}</div>
                    <div className="text-gray-400 text-sm">
                      {feedback.date} • {feedback.time}
                    </div>
                  </div>
                  <p className="text-gray-300 text-sm leading-relaxed line-clamp-3">{feedback.comment}</p>
                  <div className="flex items-center justify-between pt-2 border-t border-gray-800">
                    <Badge variant="outline" className="text-xs">
                      {feedback.category}
                    </Badge>
                    <div className="flex gap-2">
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => setSelectedFeedback(feedback)}
                        className="border-gray-600 text-gray-300 bg-transparent hover:bg-gray-700"
                      >
                        <Eye className="h-4 w-4" />
                      </Button>
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => deleteFeedback(feedback.id)}
                        className="border-red-600 text-red-400 bg-transparent hover:bg-red-600 hover:text-white"
                      >
                        <Trash2 className="h-4 w-4" />
                      </Button>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}

      {/* Feedback Detail Modal */}
      {selectedFeedback && (
        <Dialog open={true} onOpenChange={() => setSelectedFeedback(null)}>
          <DialogContent className="max-w-3xl bg-gray-900 border-gray-800">
            <DialogHeader>
              <DialogTitle className="text-white">Détails du Feedback</DialogTitle>
            </DialogHeader>
            <div className="space-y-6">
              <div className="flex items-center justify-between">
                <div>
                  <h3 className="text-xl font-bold text-white">{selectedFeedback.restaurant}</h3>
                  <p className="text-gray-400">
                    Par {selectedFeedback.client} • {selectedFeedback.date} à {selectedFeedback.time}
                  </p>
                </div>
                <div className="flex items-center gap-3">
                  <Badge className={getSentimentColor(selectedFeedback.sentiment)}>
                    {getSentimentText(selectedFeedback.sentiment)}
                  </Badge>
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => deleteFeedback(selectedFeedback.id)}
                    className="border-red-600 text-red-400 bg-transparent hover:bg-red-600 hover:text-white"
                  >
                    <Trash2 className="h-4 w-4 mr-1" />
                    Supprimer
                  </Button>
                </div>
              </div>

              <div className="flex items-center gap-2">
                {renderStars(selectedFeedback.rating)}
                <span className="text-white ml-2">{selectedFeedback.rating}/5</span>
              </div>

              <div>
                <h4 className="text-white font-medium mb-2">Commentaire complet:</h4>
                <div className="bg-gray-800 p-4 rounded-lg">
                  <p className="text-gray-300 leading-relaxed">{selectedFeedback.comment}</p>
                </div>
              </div>

              <div className="grid grid-cols-2 gap-4 pt-4 border-t border-gray-800">
                <div>
                  <span className="text-gray-400">Employé assigné:</span>
                  <p className="text-white font-medium">{selectedFeedback.employee}</p>
                </div>
                <div>
                  <span className="text-gray-400">Catégorie:</span>
                  <p className="text-white font-medium">{selectedFeedback.category}</p>
                </div>
              </div>
            </div>
          </DialogContent>
        </Dialog>
      )}
    </div>
  )
}
