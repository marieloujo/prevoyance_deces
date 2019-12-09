<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;
use App\Http\Resources\UsersResource;
class MarchandsResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return[
            'id' => $this->id,
            'credit_virtuel' => $this->credit_virtuel,
            'commission' => $this->commission,
            'user' => new UsersResource ($this->user),
        ];
    }
}
