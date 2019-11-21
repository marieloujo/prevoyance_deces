<?php

namespace App\Http\Resources\Assurer;
use App\Http\Resources\UserResource;
use App\Http\Resources\Beneficiaire\BeneficiaireResources;
use App\Http\Resources\BeneficiaireResource;
use App\Http\Resources\ClientResource;
use App\Http\Resources\DocumentResource;
use Illuminate\Http\Resources\Json\JsonResource;

class AssurerResources extends JsonResource
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
            'profession' => $this->profession,
            'user' => new UserResource($this->user),
            'beneficiares' => BeneficiaireResources::collection($this->beneficiares),
        ];
    }
}
