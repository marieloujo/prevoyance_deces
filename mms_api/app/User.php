<?php

namespace App;

use App\Models\Client;
use Illuminate\Notifications\Notifiable;
use Laravel\Passport\HasApiTokens;
use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    use HasApiTokens, Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'nom','prenom','telephone','adresse','sexe','date_naissance','situation_matrimoniale','prospect', 'actif', 'login', 'email', 'password',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    public function clients(){
        return $this->hasMany('App\Models\Client');
    }

    public function marchands(){
        return $this->hasMany('App\Models\Marchand');
    }

    public function assures(){
        return $this->hasMany('App\Models\Assure');
    }

    public function beneficiaires(){
        return $this->hasMany('App\Models\Beneficiaire');
    }


    public function super_mardands(){
        return $this->hasMany('App\Models\SuperMarchand');
    }

    public function directions(){
        return $this->hasMany('App\Models\Direction');
    }

    public function messages(){
        return $this->belongsToMany('App\User','messages')->using('App\Models\Message')->withPivot([
            'body','created_at','read_at','from_id','to_id',
        ])->withTimestamps('updated_at');
    }

    public function commune(){
        return $this->belongsTo('App\Models\Commune');
    }

    /** 
     * Find the user instance for the given telephone. 
     *  @param string $username 
     *  @return \App\User 
    */
    public function findForPassport($username) {
        return $this->where('login', $username)->first(); 
    } 
}
