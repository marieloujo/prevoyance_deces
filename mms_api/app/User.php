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
    protected $morphClass = 'App\User'; 
    
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'nom','prenom','telephone','adresse','sexe','date_naissance','situation_matrimoniale','prospect', 'actif', 'login', 'commune_id','usereable_id', 'usereable_type','email', 'password',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    public function usereable(){
        return $this->morphTo();
    }

    // public function messages(){
    //     return $this->belongsToMany('App\User','messages','from_user_id','to_user_id')->using('App\Models\Message')->withPivot([
    //         'body','read_at',
    //     ])->withTimestamps();
    // }

    public function messages(){
        return $this->hasMany('App\Models\Message','to_user_id');
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
