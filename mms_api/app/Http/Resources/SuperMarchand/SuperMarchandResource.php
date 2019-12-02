<?php

namespace App\Http\Resources\SuperMarchand;

use App\Http\Resources\CompteResource;
use App\Http\Resources\SuperMarchand\MarchandResource;
use App\Http\Resources\UserResource;
use Illuminate\Http\Resources\Json\JsonResource;

class SuperMarchandResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'matricule' => $this->matricule,
            'commission' => $this->commission,
            'user' => new UserResource($this->user),
            'marchands' => MarchandResource::collection($this->marchands),
        ];
    }
}
